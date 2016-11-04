package gavin.util;

import android.os.Environment;

/**
 * Checks the state of the external storage of the device.
 * 
 * @author kaolick
 */
public class StorageHelper
{
    // Storage states
    private boolean available, writeable;

    /**
     * Checks the external storage's state and saves it in member attributes.
     */
    private void checkStorage()
    {
        // Get the external storage's state
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED))
        {
            // Storage is available and writeable
            available = writeable = true;
        }
        else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
        {
            // Storage is only readable
            available = true;
            writeable = false;
        }
        else
        {
            // Storage is neither readable nor writeable
            available = writeable = false;
        }
    }

    /**
     * Checks the state of the external storage.
     * 
     * @return True if the external storage is available, false otherwise.
     */
    public boolean isExternalStorageAvailable()
    {
        checkStorage();

        return available;
    }

    /**
     * Checks the state of the external storage.
     * 
     * @return True if the external storage is writeable, false otherwise.
     */
    public boolean isExternalStorageWriteable()
    {
        checkStorage();

        return writeable;
    }

    /**
     * Checks the state of the external storage.
     * 
     * @return True if the external storage is available and writeable, false
     *         otherwise.
     */
    public boolean isExternalStorageAvailableAndWriteable()
    {
        checkStorage();

        if (!available)
        {
            return false;
        }
        else if (!writeable)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
