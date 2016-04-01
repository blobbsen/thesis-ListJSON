package me.blobb.listjson;

import me.blobb.listjson.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author andre.boddenberg@gmx.de
 * 
 * The class FileInfoArrayAdapter is an ArrayAdapter class to handle FileInfo Arrays.
 * 
 * @param context 	Activity that instantiate a FileInfoArrayAdapter object.
 * @param values 	FileInfo[] which should be displayed/worked with.
 *
 */
public class FileInfoArrayAdapter extends ArrayAdapter<FileInfo> 
{
	private final Context context;
	private FileInfo[] values;
	
	static class ViewHolderItem{
		
		TextView tv_name;
		TextView tv_date;
		TextView tv_size;
		ImageView iv_icon;
	}
	
	/**
	 * Constructor to instantiate a FileInfoArrayAdapter.
	 * 
	 * @param context 	Activity that instantiate a FileInfoArrayAdapter object.
	 * @param resource	View that should be used by FileInfoArrayAdapter.
	 * @param values 	FileInfo[] which should be displayed.
	 */
	public FileInfoArrayAdapter(Context context, int resource, FileInfo[] values)
	{
		super(context, resource, values);
		this.context = context;				
		this.values = values;
	}

	/**
	 * method to set content of each row (one at a time).
	 * 
	 * @param position 		position (or index) of the row that should be displayed
	 * @param convertView	convertView actually never referenced but getView method needs a convertView
	 * @param parent		ViewGroup parent	
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		ViewHolderItem viewHolder;
	
	    if(convertView==null)
	    {
	    	// inflate the layout
	    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.file_view, parent, false);
	    	
			//set up the ViewHolder
			viewHolder = new ViewHolderItem();
			
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.tv_date = (TextView) convertView.findViewById(R.id.date);
			viewHolder.tv_size = (TextView) convertView.findViewById(R.id.size);
			viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.icon);
			
			// store the holder with the view.
			convertView.setTag(viewHolder);
			
	    }else
	    {	    	
	    	viewHolder = (ViewHolderItem) convertView.getTag();
	    }

		// set text to the TextViews via FileInfo methods
	    viewHolder.tv_name.setText(values[position].getName());
	    viewHolder.tv_date.setText(values[position].getAcurateDate());
	    viewHolder.tv_size.setText(values[position].getAcurateSize());
		
		// check which icon should be set to the imageView
		if (values[position].isFolder()) 
		{
			viewHolder.iv_icon.setImageResource(R.drawable.folder_icon);
		} else {
			viewHolder.iv_icon.setImageResource(R.drawable.file_icon);
		}

		return convertView;
	}
} 