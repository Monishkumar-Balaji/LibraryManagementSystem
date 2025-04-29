package person;
public class BookStorage {
	@SuppressWarnings("unused")
	private int bookId;
	@SuppressWarnings("unused")
	private String bookTitle;
	@SuppressWarnings("unused")
	private String authorName;
	@SuppressWarnings("unused")
	private int publicationYear;
	@SuppressWarnings("unused")
	private String category;
	
	public BookStorage(int bookId,String bookTitle,String authorName,int publicationYear,int categoryId){
		this.bookId=bookId;
		this.bookTitle=bookTitle;
		this.authorName=authorName;
		this.publicationYear=publicationYear;
		
		if(categoryId==1)
			this.category="Fiction";
		else if(categoryId==2)
			this.category="Non-Fiction";
		else if(categoryId==3)
			this.category="CSE";
		else if(categoryId==4)
			this.category="IT";
		else if(categoryId==5)
			this.category="ECE";
	}
	
}
