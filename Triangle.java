import java.util.*;
import java.lang.Math;

public class Triangle
{
	// instance variables for each component of a triangle
	//      Normal(X,Y,Z); Vertex1(X,Y,Z); Vertex2(X,Y,Z); Vertex3(X,Y,Z)
	double NormX;
	double NormY;
	double NormZ;
	double V1X;
	double V1Y;
	double V1Z;
	double V2X;
	double V2Y;
	double V2Z;
	double V3X;
	double V3Y;
	double V3Z;
	static double add0 = 0;
	static double add1 = 0.5;
	
	// Factor by which the delta will be multiplied
	static double FACT = 10000;
	
	// Constructs a Triangle object
	public Triangle(double normX, double normY, double normZ, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z, double v3X, double v3Y, double v3Z)
	{
		NormX=normX;
		NormY=normY;
		NormZ=normZ;
		V1X=v1X;
		V1Y=v1Y;
		V1Z=v1Z;
		V2X=v2X;
		V2Y=v2Y;
		V2Z=v2Z;
		V3X=v3X;
		V3Y=v3Y;
		V3Z=v3Z;
	}
	
	
	// Outputs a string that would be exported as an ASCII file
	public static String toString(ArrayList<Triangle> bunch)
	{
		//~ int bunLen=bunch.size();
		String str = "solid output\n";
		for(int i = 0; i<bunch.size(); i++)
		{
			//~ System.out.println((double)i/bunLen);
			str+="  facet normal " + Double.toString(bunch.get(i).NormX) + " " + Double.toString(bunch.get(i).NormY) + " " + Double.toString(bunch.get(i).NormZ) + "\n";
			str+="    outer loop\n";
			str+="      vertex " + Double.toString(bunch.get(i).V1X) + " " + Double.toString(bunch.get(i).V1Y) + " " + Double.toString(bunch.get(i).V1Z) + "\n";
			str+="      vertex " + Double.toString(bunch.get(i).V2X) + " " + Double.toString(bunch.get(i).V2Y) + " " + Double.toString(bunch.get(i).V2Z) + "\n";
			str+="      vertex " + Double.toString(bunch.get(i).V3X) + " " + Double.toString(bunch.get(i).V3Y) + " " + Double.toString(bunch.get(i).V3Z) + "\n";
			str+="    endloop\n";
			str+="  endfacet\n";
		}
		str+="endsolid";
		return str;
	}
	
	
	// Distance Formula
	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2)+Math.pow((z2-z1),2));
	}
	
	// Implements the watermark
	public static void coolerModifier(ArrayList<Triangle> things, String binMes)
	{
		//~ int binMesLen = binMes.length();
		int count = 0;
		for(int i = 0; i<things.size(); i++)
		{
			if(count==binMes.length()) break;
			
			double normX = things.get(i).NormX;
			double v1X = things.get(i).V1X;
			double v2X = things.get(i).V2X;
			double v3X = things.get(i).V3X;
			
			double normY = things.get(i).NormY;
			double v1Y = things.get(i).V1Y;
			double v2Y = things.get(i).V2Y;
			double v3Y = things.get(i).V3Y;
			
			double normZ = things.get(i).NormZ;
			double v1Z = things.get(i).V1Z;
			double v2Z = things.get(i).V2Z;
			double v3Z = things.get(i).V3Z;
			
			double distanceOneToTwo = distance(v1X, v1Y, v1Z, v2X, v2Y, v2Z);
			double deltaX = (v3X-v2X)/distanceOneToTwo; // Take x of 3rd vertex and subtract by x of 2nd vertex and divide by distance between 1st vertex and 2nd vertex and 3rd vertex. This number is resistant across scale and translation changes because these changes would change the numerator and denominator the same amount.
			double deltaY = (v3Y-v2Y)/distanceOneToTwo;
			double deltaZ = (v3Z-v2Z)/distanceOneToTwo;
			
			
			// Encodes the delta variable
			deltaX*=FACT;
			deltaX=Math.round(deltaX);
			if(binMes.charAt(count)=='0')  deltaX+=add0;
			else deltaX+=add1;
			deltaX/=FACT;
			count++;
			if(count==binMes.length()) break;
			
			deltaY*=FACT;
			deltaY=Math.round(deltaY);
			if(binMes.charAt(count)=='0')  deltaY+=add0;
			else deltaY+=add1;
			deltaY/=FACT;
			count++;
			if(count==binMes.length()) break;
			
			deltaZ*=FACT;
			deltaZ=Math.round(deltaZ);
			if(binMes.charAt(count)=='0')  deltaZ+=add0;
			else deltaZ+=add1;
			deltaZ/=FACT;
			count++;
			
			
			
			// Undoes the operations to create delta
			v3X = (deltaX*distanceOneToTwo) + v2X;
			v3Y = (deltaY*distanceOneToTwo) + v2Y;
			v3Z = (deltaZ*distanceOneToTwo) + v2Z;
			
			//Puts coordinates back into Triangle
			Triangle edit = new Triangle(normX, normY, normZ, v1X, v1Y, v1Z, v2X, v2Y, v2Z, v3X, v3Y, v3Z);
			things.set(i, edit);
			//~ System.out.println((double)i/binMesLen);
		}
	}
	
	// Reads for the watermark
	public static String coolerReader(ArrayList<Triangle> things)
	{
		String str = "";
		for(int i = 0; i<things.size(); i++)
		{
			// System.out.println(i);
			// double normX = things.get(i).NormX;
			double v1X = things.get(i).V1X;
			double v2X = things.get(i).V2X;
			double v3X = things.get(i).V3X;
			// double normY = things.get(i).NormY;
			double v1Y = things.get(i).V1Y;
			double v2Y = things.get(i).V2Y;
			double v3Y = things.get(i).V3Y;
			
			// double normZ = things.get(i).NormZ;
			double v1Z = things.get(i).V1Z;
			double v2Z = things.get(i).V2Z;
			double v3Z = things.get(i).V3Z;
			
			double distanceOneToTwo = distance(v1X, v1Y, v1Z, v2X, v2Y, v2Z);
			
			double deltaX = (v3X-v2X)/distanceOneToTwo;
			double deltaY = (v3Y-v2Y)/distanceOneToTwo;
			double deltaZ = (v3Z-v2Z)/distanceOneToTwo;
			
			
			// Mods each delta to determine a 0-bit or a 1-bit
			double mod1 = 1;
			char a = '0';
			char b = '1';
			
			if(((deltaX*FACT)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((deltaX*FACT)%mod1+mod1)%mod1<.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
			
			if(((deltaY*FACT)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((deltaY*FACT)%mod1+mod1)%mod1<.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
			
			
			if(((deltaZ*FACT)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((deltaZ*FACT)%mod1+mod1)%mod1<0.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
		}
		return str;
	}
}
