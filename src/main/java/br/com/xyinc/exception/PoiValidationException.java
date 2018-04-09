package br.com.xyinc.exception;

public class PoiValidationException extends Exception{

	private static final long serialVersionUID = 1L;

	private Integer code;
	
	public PoiValidationException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

}
