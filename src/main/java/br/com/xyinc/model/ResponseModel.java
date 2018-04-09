package br.com.xyinc.model;

public class ResponseModel {
	
	private Integer status;
	private String description;
	private Long id;
	
	public ResponseModel() {
	}
	
	public ResponseModel(Integer status, String description) {
		this.status = status;
		this.description = description;
	}

	public ResponseModel(Integer status, String description, Long id) {
		this.status = status;
		this.description = description;
		this.id = id;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseModel [status=");
		builder.append(status);
		builder.append(", description=");
		builder.append(description);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

	
}
