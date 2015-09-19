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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class DatasetLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "dataSetId", referencedColumnName = "id")
	private Dataset dataset;
	private String plotUrl;
	private String dataUrl;
	@ManyToOne(optional = false)
	@JoinColumn(name = "originalDatasetId", referencedColumnName = "id")
	private Dataset originalDataset;
	@ManyToOne(optional = false)
	@JoinColumn(name = "outputDatasetId", referencedColumnName = "id")
	private Dataset outputDataset;
	
	public DatasetLog() {
		
	}
	
	public DatasetLog( Dataset dataset,
			String plotUrl, String dataUrl, Dataset originalDataset,
			Dataset outputDataset) {
		super();
		this.dataset = dataset;
		this.plotUrl = plotUrl;
		this.dataUrl = dataUrl;
		this.originalDataset = originalDataset;
		this.outputDataset = outputDataset;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataSet(Dataset dataset) {
		this.dataset = dataset;
	}

	public String getPlotUrl() {
		return plotUrl;
	}

	public void setPlotUrl(String plotUrl) {
		this.plotUrl = plotUrl;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public Dataset getOriginalDataset() {
		return originalDataset;
	}

	public void setOriginalDataset(Dataset originalDataset) {
		this.originalDataset = originalDataset;
	}

	public Dataset getOutputDataset() {
		return outputDataset;
	}

	public void setOutputDataset(Dataset outputDataset) {
		this.outputDataset = outputDataset;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "DatasetLog [id=" + id +  ", dataSet=" + dataset + ", plotUrl="
				+ plotUrl + ", dataUrl=" + dataUrl + ", originalDataSet="
				+ originalDataset + ", outputDataSet=" + outputDataset + "]";
	}
	
	
}
