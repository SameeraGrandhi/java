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

import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.extended.kubectl.exception.KubectlException;
import io.kubernetes.client.extended.kubectl.rollout.response.RolloutHistory;
import java.io.InputStream;
import java.util.Set;

public interface RolloutAction<ApiType extends KubernetesObject> {

  public Set<RolloutHistory<ApiType>> history();

  public ApiType undo();

  public ApiType pause() throws KubectlException;

  public ApiType restart() throws KubectlException;

  public ApiType resume() throws KubectlException;

  public InputStream status();
}
