/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Named
@Singleton
public interface WorkflowRepository extends CrudRepository<Workflow, Long> {
    List<Workflow> findByUserID(Long id);
    Workflow findById(Long id);

    @Query(value = "select w.* from Workflow w where (w.groupId = 0)", nativeQuery = true)
    List<Workflow> findPubicWorkflow();

    @Query(value = "select * from Workflow where id in (select workflowId from WorkflowAndTags where (tagId = ?1))", nativeQuery = true)
    List<Workflow> findByTagId(Long tag);
    
    @Query(value = "select * from Workflow where wfTitle like ?1", nativeQuery = true)
    List<Workflow> findByTitle(String title);

    List<Workflow> findByGroupId(Long id);

    @Query(value = "select w.* from Workflow w order by w.viewCount desc LIMIT 3", nativeQuery = true)
    List<Workflow> findTop3Workflow();
}