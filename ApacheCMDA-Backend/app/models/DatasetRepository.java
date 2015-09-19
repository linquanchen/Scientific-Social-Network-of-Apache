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

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public interface DatasetRepository extends CrudRepository<Dataset, Long> {
	List<Dataset> findByNameLikeAndAgencyIdLikeAndGridDimensionLikeAndPhysicalVariableLikeAndInstrument_Id(String name, String agencyId, String gridDimension, String physicalVariable,long instrumentId);
	List<Dataset> findByNameLikeAndAgencyIdLikeAndGridDimensionLikeAndPhysicalVariableLike(String name, String agencyId, String gridDimension, String physicalVariable);
	List<Dataset> findByvariableNameInWebInterface(String variableNameInWebInterface);
	List<Dataset> findByNameLikeAndAgencyIdLikeAndGridDimensionLikeAndPhysicalVariableLikeAndInstrument_IdAndStartTimeLessThanEqualOrEndTimeGreaterThanEqual(String name, String agencyId, String gridDimension, String physicalVariable, long instrumentId, Date startTime, Date endTime);
	List<Dataset> findByNameLikeAndAgencyIdLikeAndGridDimensionLikeAndPhysicalVariableLikeAndStartTimeLessThanEqualOrEndTimeGreaterThanEqual(String name, String agencyId, String gridDimension, String physicalVariable, Date startTime, Date endTime);
	
	//New Dataset Search 
	@Query(value = "select d.* from Dataset d where ((d.name like ?1) and (d.agencyId like ?2) and (d.gridDimension like ?3) and (d.physicalVariable like ?4) and (d.instrumentId = ?5)) and ((d.startTime between ?6 and ?7) or (d.endTime between ?6 and ?7) or (d.startTime <= ?6 and d.endTime >= ?7))", nativeQuery = true)
	List<Dataset> findDatasetWithInstrument_Id(String name, String agencyId, String gridDimension, String physicalVariable, long instrumentId, Date startTime, Date endTime);
	
	@Query(value = "select d.* from Dataset d where ((d.name like ?1) and (d.agencyId like ?2) and (d.gridDimension like ?3) and (d.physicalVariable like ?4)) and ((d.startTime between ?5 and ?6) or (d.endTime between ?5 and ?6) or (d.startTime <= ?5 and d.endTime >= ?6))", nativeQuery = true)
	List<Dataset> findDataset(String name, String agencyId, String gridDimension, String physicalVariable, Date startTime, Date endTime);
	
	@Query(value = "select d.* from Dataset d where ((d.name like ?1) and (d.agencyId like ?2) and (d.gridDimension like ?3) and (d.physicalVariable like ?4) and (d.source like ?5)) and ((d.startTime between ?6 and ?7) or (d.endTime between ?6 and ?7) or (d.startTime <= ?6 and d.endTime >= ?7))", nativeQuery = true)
	List<Dataset> findDatasetWithInstrument(String name, String agencyId, String gridDimension, String physicalVariable, String source, Date startTime, Date endTime);
	
}