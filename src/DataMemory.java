
public class DataMemory 
{
	private MemoryBucket[] theBuckets;
	
	//must be a multiple of 4 (5000, 5004, 5008, 5012, etc)
	public DataMemory(int byteSize)
	{
		this.theBuckets = new MemoryBucket[byteSize/4];
		int currentLocation = 5000; //first memory address
		for(int i = 0; i < this.theBuckets.length; i++)
		{
			this.theBuckets[i] = new MemoryBucket(currentLocation);
			currentLocation += 4;
		}
	}
	
	//assuming we are always asking for 4 bytes or 32 bits
	public int getAddressForNewMemory() //gives an available memory location
	{
		for(MemoryBucket mb : this.theBuckets) //forEach looping through the buckets
		{
			if(!mb.isInUse()) //if the bucket we're looking at is not in use
			{
				mb.setInUse(true); //mark him in use because we've now found open memory to use
				return mb.getLocation(); //return the location, end method
			}
		}
		throw new RuntimeException("You're out of memory"); //if this is thrown, we didn't find any memory that's not in use
	}
}
