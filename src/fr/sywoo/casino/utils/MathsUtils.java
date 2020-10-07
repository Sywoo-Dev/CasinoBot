package fr.sywoo.casino.utils;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
public class MathsUtils {

	private static NavigableMap<Long, String> suffixes = new TreeMap<> ();
	static {
	  suffixes.put(1_000L, "k");
	  suffixes.put(1_000_000L, "M");
	  suffixes.put(1_000_000_000L, "G");
	  suffixes.put(1_000_000_000_000L, "T");
	  suffixes.put(1_000_000_000_000_000L, "P");
	  suffixes.put(1_000_000_000_000_000_000L, "E");
	}

	
	public boolean isInrange(double value, int min, int max){
		
		return value > min && value < max;
		
	}

	public String format(long value) {

	  if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
	  if (value < 0) return "-" + format(-value);
	  if (value < 1000) return Long.toString(value); 

	  Entry<Long, String> e = suffixes.floorEntry(value);
	  Long divideBy = e.getKey();
	  String suffix = e.getValue();

	  long truncated = value / (divideBy / 10);
	  boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
	  return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}
    public boolean isInteger(String val) {
        byte[] bytes = val.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (!Character.isDigit((char) bytes[i])) {
                return false;
            }
        }
        return true;
    }
    public int getPourcent(int multiply, int divise){
    	return (multiply*100) / divise;
    }
    
    public int getControledRandom(int higher, int lower){
    	return (int) ((Math.random() * (higher*lower)) + lower);
    }
    public double getControledRandom(double higher, double lower){
    	return ((Math.random() * (higher*lower)) + lower);
    }
    public double getRealPitch(double pitch){
    	return ((pitch + 90) * Math.PI) / 100;
    }
    public double getRealYaw(double yaw){
    	return ((yaw + 90) * Math.PI) / 100;
    }
	
}