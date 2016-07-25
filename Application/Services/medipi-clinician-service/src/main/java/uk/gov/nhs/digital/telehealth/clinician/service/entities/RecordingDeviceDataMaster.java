/*
 *
 * Copyright (C) 2016 Krishna Kuntala @ Mastek <krishna.kuntala@mastek.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.gov.nhs.digital.telehealth.clinician.service.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//@formatter:off
@Entity
@Table(name = "recording_device_data")
@NamedQueries({
	@NamedQuery(name = "RecordingDeviceDataMaster.fetchRecentMeasurements", query = "SELECT recordingDeviceDataMaster FROM RecordingDeviceDataMaster recordingDeviceDataMaster"
			+ " JOIN recordingDeviceDataMaster.patient patient"
          	+ " JOIN recordingDeviceDataMaster.recordingDeviceAttribute recordingDeviceAttribute"
          	+ " JOIN recordingDeviceDataMaster.recordingDeviceAttribute.recordingDevice recordingDevice"
           	+ " WHERE recordingDeviceDataMaster.dataId in (SELECT MAX(rdm.dataId) as dataId FROM RecordingDeviceDataMaster rdm"
           		+ " WHERE rdm.patient.patientId = :patientId"
           		+ " GROUP BY rdm.recordingDeviceAttribute.attributeId)"
           	+ " AND patient.patientId = :patientId"
           	+" ORDER BY recordingDevice.typeId ASC"),

	@NamedQuery(name = "RecordingDeviceDataMaster.fetchMeasurements", query = "SELECT recordingDeviceDataMaster FROM RecordingDeviceDataMaster recordingDeviceDataMaster"
			+ " JOIN recordingDeviceDataMaster.patient patient"
          	+ " JOIN recordingDeviceDataMaster.recordingDeviceAttribute recordingDeviceAttribute"
           	+ " WHERE patient.patientId = :patientId"
           	+ " AND recordingDeviceAttribute.attributeId = :attributeId"
           	+" ORDER BY recordingDeviceDataMaster.dataValueTime ASC")
})
//@formatter:on
public class RecordingDeviceDataMaster {

	@Id
	@Column(name = "data_id")
	private Long dataId;

	@Column(name = "data_value")
	private String dataValue;

	@Column(name = "data_value_time")
	private Timestamp dataValueTime;

	@Column(name = "downloaded_time")
	private Timestamp submittedTime;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private PatientMaster patient;

	@ManyToOne
	@JoinColumn(name = "attribute_id")
	private RecordingDeviceAttributeMaster recordingDeviceAttribute;

	public RecordingDeviceDataMaster() {
	}

	public RecordingDeviceDataMaster(final Long dataId, final String dataValue, final Timestamp dataValueTime, final Timestamp submittedTime, final PatientMaster patient, final RecordingDeviceAttributeMaster recordingDeviceAttribute) {
		this();
		this.dataId = dataId;
		this.dataValue = dataValue;
		this.dataValueTime = dataValueTime;
		this.submittedTime = submittedTime;
		this.patient = patient;
		this.recordingDeviceAttribute = recordingDeviceAttribute;
	}

	public RecordingDeviceDataMaster(final Long dataId, final String dataValue, final Timestamp dataValueTime, final Timestamp submittedTime) {
		this(dataId, dataValue, dataValueTime, submittedTime, null, null);
	}

	public RecordingDeviceDataMaster(final Long dataId, final String dataValue, final Timestamp dataValueTime, final Timestamp submittedTime, final PatientMaster patient) {
		this(dataId, dataValue, dataValueTime, submittedTime, patient, null);
	}

	public RecordingDeviceDataMaster(final Long dataId, final String dataValue, final Timestamp dataValueTime, final Timestamp submittedTime, final RecordingDeviceAttributeMaster recordingDeviceAttribute) {
		this(dataId, dataValue, dataValueTime, submittedTime, null, recordingDeviceAttribute);
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(final Long dataId) {
		this.dataId = dataId;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(final String dataValue) {
		this.dataValue = dataValue;
	}

	public Timestamp getDataValueTime() {
		return dataValueTime;
	}

	public void setDataValueTime(final Timestamp dataValueTime) {
		this.dataValueTime = dataValueTime;
	}

	public Timestamp getSubmittedTime() {
		return submittedTime;
	}

	public void setSubmittedTime(final Timestamp submittedTime) {
		this.submittedTime = submittedTime;
	}

	public PatientMaster getPatient() {
		return patient;
	}

	public void setPatient(final PatientMaster patient) {
		this.patient = patient;
	}

	public RecordingDeviceAttributeMaster getRecordingDeviceAttribute() {
		return recordingDeviceAttribute;
	}

	public void setRecordingDeviceAttribute(final RecordingDeviceAttributeMaster recordingDeviceAttribute) {
		this.recordingDeviceAttribute = recordingDeviceAttribute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dataId == null ? 0 : dataId.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		RecordingDeviceDataMaster other = (RecordingDeviceDataMaster) obj;
		if(dataId == null) {
			if(other.dataId != null) {
				return false;
			}
		} else if(!dataId.equals(other.dataId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RecordingDeviceDataMaster [dataId=" + dataId + ", dataValue=" + dataValue + ", dataValueTime=" + dataValueTime + ", submittedTime=" + submittedTime + ", patient=" + patient + ", recordingDeviceAttribute=" + recordingDeviceAttribute + "]";
	}
}