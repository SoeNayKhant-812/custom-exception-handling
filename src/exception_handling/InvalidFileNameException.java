package exception_handling;

class InvalidFileNameException extends Exception {
	private static final long serialVersionUID = 9521376550087638L;

	public InvalidFileNameException(String message) {
		super(message);
	}
}
