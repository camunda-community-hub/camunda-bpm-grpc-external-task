/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.grpc.client.task;

import org.camunda.bpm.grpc.FetchAndLockResponse;

/**
 * Client-side service encapsulating external task service calls
 */
public interface ExternalTaskService {

  /**
   * Unlocks a task and clears the tasks lock expiration time and worker id.
   *
   * @param externalTask which will be unlocked
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws ConnectionLostException if the connection could not be established
   */
  void unlock(FetchAndLockResponse externalTask);

  /**
   * Completes a task.
   *
   * @param externalTask which will be completed
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws NotAcquiredException if the task's most recent lock could not be acquired
   * @throws NotResumedException if the corresponding process instance could not be resumed
   * @throws ConnectionLostException if the connection could not be established
   * @throws ValueMapperException
   * <ul>
   *   <li> if an object cannot be serialized
   *   <li> if no 'objectTypeName' is provided for non-null value
   *   <li> if value is of type abstract
   *   <li> if no suitable serializer could be found
   * </ul>
   */
  void complete(FetchAndLockResponse externalTask);

  /**
   * Completes a task.
   *
   * @param externalTask  which will be completed
   * @param variables     are set in the task's ancestor execution hierarchy The key and the value represent
   *                      the variable name and its value. Map can consist of both typed and untyped variables.
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws NotAcquiredException if the task's most recent lock could not be acquired
   * @throws NotResumedException if the corresponding process instance could not be resumed
   * @throws ConnectionLostException if the connection could not be established
   * @throws ValueMapperException
   * <ul>
   *   <li> if an object cannot be serialized
   *   <li> if no 'objectTypeName' is provided for non-null value
   *   <li> if value is of type abstract
   *   <li> if no suitable serializer could be found
   * </ul>
   */
//  void complete(FetchAndLockResponse externalTask, Map<String, Object> variables);

  /**
   * Completes a task.
   *
   * @param externalTask    which will be completed
   * @param variables       are set in the task's ancestor execution hierarchy. The key and the value represent
   *                        the variable name and its value. Map can consist of both typed and untyped variables.
   * @param localVariables  are set in the execution of the external task instance. The key and the value represent
   *                        the variable name and its value. Map can consist of both typed and untyped variables.
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws NotAcquiredException if the task's most recent lock could not be acquired
   * @throws NotResumedException if the corresponding process instance could not be resumed
   * @throws ConnectionLostException if the connection could not be established
   * @throws ValueMapperException
   * <ul>
   *   <li> if an object cannot be serialized
   *   <li> if no 'objectTypeName' is provided for non-null value
   *   <li> if value is of type abstract
   *   <li> if no suitable serializer could be found
   * </ul>
   */
//  void complete(FetchAndLockResponse externalTask, Map<String, Object> variables, Map<String, Object> localVariables);

  /**
   * Reports a failure to execute a task. A number of retries and a timeout until
   * the task can be specified. If the retries are set to 0, an incident for this
   * task is created.
   *
   * @param externalTask which is meant to notify a failure for
   * @param errorMessage indicates the reason of the failure.
   * @param errorDetails provides a detailed error description.
   * @param retries      specifies how often the task should be retried. Must be &gt;= 0.
   *                     If 0, an incident is created and the task cannot be fetched anymore
   *                     unless the retries are increased again. The incident's message is set
   *                     to the errorMessage parameter.
   * @param retryTimeout specifies a timeout in milliseconds before the external task
   *                     becomes available again for fetching. Must be &gt;= 0.
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws NotAcquiredException if the task's most recent lock could not be acquired
   * @throws NotResumedException if the corresponding process instance could not be resumed
   * @throws ConnectionLostException if the connection could not be established
   */
  void handleFailure(FetchAndLockResponse externalTask, String errorMessage, String errorDetails, int retries, long retryTimeout);

  /**
   * Reports a business error in the context of a running task.
   * The error code must be specified to identify the BPMN error handler.
   *
   * @param externalTask which is meant to notify a BPMN error for
   * @param errorCode    that indicates the predefined error. The error code
   *                     is used to identify the BPMN error handler.
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws NotAcquiredException if the task's most recent lock could not be acquired
   * @throws NotResumedException if the corresponding process instance could not be resumed
   * @throws ConnectionLostException if the connection could not be established
   */
  void handleBpmnError(FetchAndLockResponse externalTask, String errorCode);

  /**
   * Reports a business error in the context of a running task.
   * The error code must be specified to identify the BPMN error handler.
   *
   * @param externalTask which is meant to notify a BPMN error for
   * @param errorCode    that indicates the predefined error. The error code
   *                     is used to identify the BPMN error handler.
   * @param errorMessage which will be passed when the BPMN error is caught
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws NotAcquiredException if the task's most recent lock could not be acquired
   * @throws NotResumedException if the corresponding process instance could not be resumed
   * @throws ConnectionLostException if the connection could not be established
   */
  void handleBpmnError(FetchAndLockResponse externalTask, String errorCode, String errorMessage);

  /**
   * Reports a business error in the context of a running task.
   * The error code must be specified to identify the BPMN error handler.
   *
   * @param externalTask which is meant to notify a BPMN error for
   * @param errorCode    that indicates the predefined error. The error code
   *                     is used to identify the BPMN error handler.
   * @param errorMessage which will be passed when the BPMN error is caught
   * @param variables    which will be passed to the execution when the BPMN error is caught
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws NotAcquiredException if the task's most recent lock could not be acquired
   * @throws NotResumedException if the corresponding process instance could not be resumed
   * @throws ConnectionLostException if the connection could not be established
   */
//  void handleBpmnError(FetchAndLockResponse externalTask, String errorCode, String errorMessage, Map<String, Object> variables);

  /**
   * Extends the timeout of the lock by a given amount of time.
   *
   * @param externalTask which lock will be extended
   * @param newDuration  specifies the the new lock duration in milliseconds
   *
   * @throws NotFoundException if the task has been canceled and therefore does not exist anymore
   * @throws NotAcquiredException if the task's most recent lock could not be acquired
   * @throws ConnectionLostException if the connection could not be established
   */
  void extendLock(FetchAndLockResponse externalTask, long newDuration);
}
