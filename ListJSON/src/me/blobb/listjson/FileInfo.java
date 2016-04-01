package me.blobb.listjson;

import java.util.Date;

/**
 * @author andre.boddenberg@gmx.de
 * 
 * The FileInfo class provides <i>one</i> constructor to instantiate a FileInfo object.<br>
 * A FileInfo object can be used to handle the information of folders and files.<br><br>
 * Check the parameters for more details.<br><br>
 * 
 * Besides the typical getter and setter this class provides methods, which transform the long values<br>
 * of size and date into representative String (i. e. size=>"12.5 kB"  date=>"11 Sep 1987")
 * 
 * @param size		long -> size of the FileInfo object
 * @param date		long (1.1.1970) -> date of the FileInfo object.
 * @param name		String -> name of the FileInfo object.
 * @param folder	boolean -> variable to indicate whether the FileInfo object is a folder or a file.
 */
public class FileInfo
{
	private long size;
	private long date;
	private String name;
	private boolean folder;
	
	/**
	 * Constructor to instantiate a FileInfo object.<br>
	 * There is no default Constructor, so you <i>must</i> instantiate <br>
	 * a FileInfo object with <i>all four</i> parameters.
	 * 
	 * @param size		long -> size the FileInfo object should have.
	 * @param date		long (1.1.1970) -> date the FileInfo object should have.
	 * @param name		String -> name the FileInfo object should have.
	 * @param folder	boolean -> set true if FileInfo should be a folder.
	 */
	public FileInfo(long size, long date, String name,  boolean folder)
	{
		this.name = name;
		this.size = size;
		this.date = date;
		this.folder = folder;
	}

	/**
	 * method to get a representative String of the FileInfo object.<br><br>
	 * NOTE: if you want to implement a toJSON method you could take this as a template.
	 * 
	 * @return returns a representative String of the FileInfo (may useful for testing).
	 */
	public String toString()
	{
		return "name: " + this.name + 
				", date(long): " + String.valueOf(this.date) + 
				", size (in Byte): " + this.size + 
				", is folder = " + this.isFolder();
	}
	
	/**
	 * getter: gets the size of the FileInfo object as long
	 * 
	 * @return returns the size of the FileInfo object as long
	 */
	public long getRawSize()
	{
		return size;
	}
	
	/**
	 * Method to get a representative String of the size of the FileInfo object.<br>
	 * This method divides the FileInfo size(long) by 1024 until it's smaller than 1024.<br>
	 * While it counts each division and use it as an indicator for the "unit-prefix".<br>
	 * Afterwards it rounds the size to one digit accuracy.<br><br>
	 * 
	 * NOTE: if you consider implementing "Locale" to get date in correct language, 
	 * you could use it to define whether to show a dot or a comma.
	 * 
	 * @return returns a representative String of the size of the FileInfo object (i. e. "12.5 kB")
	 */
	public String getAcurateSize()
	{
		double dSize = (double) this.size;			// casting size to double to have a floating point variable
		String dimStr = null;						// String for the "unit-prefix"
		int dim = 0;								// counts the times of dividing

		// divide size until it's smaller than 1024
		while(dSize > 1024.0D)					
		{
			dSize = dSize/1024.0D;
			dim++;
		}
		
		// evaluate the "division-counter"
		switch (dim)							
		{
			case 0:	dimStr =" B";
					break;
			case 1:	dimStr =" kB";
					break;
			case 2:	dimStr =" MB";
					break;
			case 3: dimStr =" GB";
					break;
			case 4: dimStr =" TB";
					break;
			case 5:	dimStr =" PB";
					break;
			case 6: dimStr =" EB";
		}
		
		// round to one digit accuracy
		dSize = (double)Math.round(dSize * 10) / 10;		
		
		return String.valueOf(dSize) + dimStr;
	}

	/**
	 * set the size(long) of the FileInfo object
	 * 
	 * @param size pass size of FileInfo object as long
	 */
	protected void setRawSize(long size)  
	{
		this.size = size;
	}
	
	/**
	 * getter: date of FileInfo object as long
	 * 
	 * @return returns the date as long (1.1.1970)
	 */
	public long getRawDate()
	{
		return date;
	}

	/**
	 * This method turns the date variable(long) of the FileInfo object into a representative String<br><br>
	 * 
	 * NOTE: This method can be improved a lot. Especially if you want to implement a possibility to 
	 * 		 choose what "kind/types" of the date should be displayed (and in which language).
	 * 
	 * @return returns the date of the FileInfo object as a String "DD MMM YYYY".
	 */
	public String getAcurateDate(){
		Date df = new Date(this.date * 1000);
		
		String dStr = df.toString();
		
		//String dayWord = dStr.substring(0, dStr.indexOf(" ")); 	    not used in the ListJSONTest 
		dStr = dStr.substring(dStr.indexOf(" ")+1);
		
		String monthWord = dStr.substring(0, dStr.indexOf(" "));
		dStr = dStr.substring(dStr.indexOf(" ")+1);
		
		String dayNumber = dStr.substring(0, dStr.indexOf(" "));
		dStr = dStr.substring(dStr.indexOf(" ")+1);
		
		//String time =  dStr.substring(0, dStr.indexOf(" "));			not used in the ListJSONTest 
		dStr = dStr.substring(dStr.indexOf(" ")+1);
				
		//String timezone = dStr.substring(0, dStr.indexOf(" "));		not used in the ListJSONTest 
		dStr = dStr.substring(dStr.indexOf(" ")+1);
		
		String year = dStr;
		
		return dayNumber + " " + monthWord + " " + year;
	}
	
	/**
	 * setter: sets the date of the FileInfo object
	 * 
	 * @param date pass new date as long (1.1.1970)
	 */
	protected void setRawDate(long date)
	{
		this.date = date;
	}
	
	/**
	 * getter: returns the name of the FileInfo object as a String
	 * 
	 * @return name of the FileInfo object
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * setter: sets the name of the FileInfo object
	 * 
	 * @param name pass the name of FileInfo object (folder/file) as String
	 */
	protected void setName(String name)
	{
		this.name = name;
	}

	/**
	 * method to get information about file or folder of FileInfo object.
	 * 
	 * @return folder returns true if FileInfo object is a folder 
	 */
	public boolean isFolder()
	{
		return folder;
	}

	/**
	 * setter: set the the value of folder to "true" or "false".
	 * 
	 * @param folder
	 */
	protected void setFolder(boolean folder)
	{
		this.folder = folder;
	}

}
