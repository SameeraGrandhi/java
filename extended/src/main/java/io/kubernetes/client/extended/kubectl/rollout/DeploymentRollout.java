/*
Copyright 2020 The Kubernetes Authors.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package io.kubernetes.client.extended.kubectl.rollout;

import io.kubernetes.client.common.KubernetesListObject;
import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.extended.kubectl.exception.KubectlException;
import io.kubernetes.client.extended.kubectl.rollout.response.RolloutHistory;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1ReplicaSet;
import io.kubernetes.client.openapi.models.V1ReplicaSetList;
import io.kubernetes.client.util.generic.GenericKubernetesApi;
import io.kubernetes.client.util.generic.KubernetesApiResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DeploymentRollout extends Rollout<V1Deployment> {

  private static final String PAUSE_PATCH = "{\"spec\":{\"paused\":%s}}";
  private static final String REVISION_ANNOTATION = "deployment.kubernetes.io/revision";

  public DeploymentRollout(
      GenericKubernetesApi<V1Deployment, ? extends KubernetesListObject> api, String name) {
    super(api, name);
  }

  public DeploymentRollout(
      GenericKubernetesApi<V1Deployment, ? extends KubernetesListObject> api,
      String name,
      String namespace) {
    super(api, name, namespace);
  }

  @Override
  public Map<Long, RolloutHistory> history() throws KubectlException {
    validate();
    V1Deployment deployment = getResource();
    String labelSelector =
        deployment.getSpec().getSelector().getMatchLabels().entrySet().stream()
            .map(
                each -> {
                  return each.getKey() + "=" + each.getValue();
                })
            .collect(Collectors.joining(","));
    AppsV1Api appsV1Api = new AppsV1Api(getApiClient());
    Map<Long, RolloutHistory> historyInfo = new HashMap<>();
    try {
      V1ReplicaSetList replicaSetList =
          appsV1Api.listNamespacedReplicaSet(
              getNamespace(), "true", false, null, null, labelSelector, null, null, null, null);
      if (replicaSetList != null && replicaSetList.getItems() != null) {
        for (V1ReplicaSet each : replicaSetList.getItems()) {
          String revision =
              each.getMetadata().getAnnotations().getOrDefault(REVISION_ANNOTATION, null);
          if (revision != null) {
            RolloutHistory rolloutHistory = new RolloutHistory(each.getSpec().getTemplate());
            historyInfo.put(Long.parseLong(revision), rolloutHistory);
          }
        }
        ;
      }
    } catch (ApiException e) {
      throw new KubectlException("Error while fetching replicasets");
    }
    return historyInfo;
  }

  @Override
  public V1Deployment undo() {
    return null;
  }

  @Override
  public InputStream status() {
    return null;
  }

  @Override
  public V1Deployment resume() throws KubectlException {
    validate();
    V1Deployment deployment = getResource();

    if (!deployment.getSpec().getPaused()) {
      throw new KubectlException("Resource " + getName() + " is not paused");
    }

    V1Patch patch = new V1Patch(String.format(PAUSE_PATCH, Boolean.FALSE.toString()));
    KubernetesApiResponse<V1Deployment> patchResponse =
        getApi().patch(getNamespace(), getName(), V1Patch.PATCH_FORMAT_JSON_MERGE_PATCH, patch);
    if (patchResponse == null || patchResponse.getObject() == null) {
      throw new KubectlException(
          "Failed to resume resource "
              + getName()
              + " returned with status code "
              + patchResponse.getHttpStatusCode());
    }
    return patchResponse.getObject();
  }

  @Override
  public V1Deployment restart() throws KubectlException {
    validate();
    V1Deployment deployment = getResource();

    // Cannot restart a paused deployment
    if (deployment.getSpec().getPaused()) {
      throw new KubectlException(
          "Resource " + getName() + " is paused, run `rollout resume` first");
    }

    KubernetesApiResponse<V1Deployment> patchResponse =
        getApi()
            .patch(
                getNamespace(),
                getName(),
                V1Patch.PATCH_FORMAT_JSON_MERGE_PATCH,
                getRestartPatch());
    if (patchResponse == null || patchResponse.getObject() == null) {
      throw new KubectlException("Failed to restart resource " + getName());
    }
    return patchResponse.getObject();
  }

  @Override
  public V1Deployment pause() throws KubectlException {
    validate();
    V1Deployment deployment = getResource();

    // Cannot pause an already paused deployment
    if (deployment.getSpec().getPaused()) {
      throw new KubectlException("Resource " + getName() + " is already paused");
    }

    V1Patch patch = new V1Patch(String.format(PAUSE_PATCH, Boolean.TRUE.toString()));
    KubernetesApiResponse<V1Deployment> patchResponse =
        getApi().patch(getNamespace(), getName(), V1Patch.PATCH_FORMAT_JSON_MERGE_PATCH, patch);
    if (patchResponse == null || patchResponse.getObject() == null) {
      throw new KubectlException("Failed to pause resource " + getName());
    }
    return patchResponse.getObject();
  }
}