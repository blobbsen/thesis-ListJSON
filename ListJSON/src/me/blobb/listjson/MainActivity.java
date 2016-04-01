package me.blobb.listjson;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.blobb.listjson.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @author andre.boddenberg@gmx.de
 * 
 * The MainActivity class provides a simple Activity with one ListView.<br>
 * The class instantiate a listView, parses a JSONfile to a representative String<br>
 * and parses this String to a FileInfo[]. <br><br>
 * 
 * The objects of the FileInfo[] are each displayed via an ArrayAdapter in a rowView (FileInfoArrayAdapter).<br>
 * For more details read the FileInfoArrayAdapter doc. 
 * 
 */
public class MainActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// call super Constructor and set View layout
		super.onCreate(savedInstanceState);							
	    setContentView(R.layout.list_view);
	    
	    // instantiate the default android list view
	    ListView lv = (ListView) findViewById(R.id.listview);
		
	    // getting file.json and creating FileInfo[]
		String json = parseJSONStringFromAssets("files.json");							
		FileInfo[] arFi = parseJSONStringToFileInfoArray(json);
		
		// instantiate a new FileInfoArrayAdapter to display FileInfo[] objects via list_view
	    FileInfoArrayAdapter adapter = new FileInfoArrayAdapter(this, R.layout.list_view, arFi);
	    lv.setAdapter(adapter);
	  }
	
	/**
	 * Method parses a JSONFile to a String and returns it.
	 * 
	 * @param file Name of a JSONfile that should be parsed to a String.<br>
	 * NOTE: If file is in a subfolder of /assets/.. you must pass the path.
	 * 
	 * @return Returns representative String of the parsed JSONfile.
	 */
	private String parseJSONStringFromAssets(String file) 
	{	
		InputStream is;
		byte[] buffer = null;
		
		try 
		{
			is = getAssets().open(file);
			
			buffer = new byte[is.available()];
			
			is.read(buffer);
			is.close();
			
			return new String(buffer, "UTF-8");
		
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method to parse the JSONString to a FileInfo Array
	 * 
	 * @param  jsonString representative String of a JSONfile
	 * @return Returns a FileInfo[], which contains all objects of the folder and file Array in the passed jsonString.
	 */
	private FileInfo[] parseJSONStringToFileInfoArray(String jsonString)
	{
		FileInfo[] arFi = null;	
		
		try 
		{
			//instantiating JSONObject and jump into the root object
			JSONObject jsParent = new JSONObject(jsonString);
			jsParent = jsParent.getJSONObject("folder");				
			
			// creating JSONArray of the arrays folder and file
			JSONArray jsArFolders = jsParent.getJSONArray("folder");	// creating array
			JSONArray jsArFiles =  jsParent.getJSONArray("file");		// creating array

			// re-instantiating arFi and setting its length
			arFi = new FileInfo[jsArFolders.length() + jsArFiles.length()];
			
			// temporary JSONObject to work through every JSONObject inside the two arrays.
			JSONObject temp = null;
			
			// creating FileInfo objects of each JSONObjects inside "JSON folder array" 
			// and add it to arFi. 
			for(int i=0; i<jsArFolders.length(); i++)
			{
				temp = jsArFolders.getJSONObject(i);
				arFi[i] = new FileInfo(temp.getLong("size"), temp.getLong("date"), temp.getString("name"), true); 
			}
			
			// creating FileInfo objects of each JSONObjects inside "JSON file array" 
			// and add it to arFi.
			for(int i=0; i<jsArFiles.length() ; i++)
			{
				temp = jsArFiles.getJSONObject(i);
				arFi[i + jsArFolders.length()] = new FileInfo(temp.getLong("size"), temp.getLong("date"), temp.getString("name"), false); 
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	  	return arFi;  	
	}
}
	