package exception_handling;

class InvalidFileNameException extends Exception {
	private static final long serialVersionUID = 9521376550087638L;
	private String filename;

	public InvalidFileNameException(String filename) {
		super("Invalid file name: " + filename);
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public String getSuggestedFix() {
		return "File name should only contain letters, numbers, underscores, hypen and periods.";
	}

}
