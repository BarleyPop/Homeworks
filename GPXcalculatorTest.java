/*Alejandra Murphy Judy
 * CIS 350
 */

package edu.upenn.cis350.gpx;
import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Date;
import java.util.ArrayList;


public class GPXcalculatorTest {

	private Date testDate= new Date(2012, 12, 21);
	private GPXtrkpt point1 = new GPXtrkpt(0,1,testDate);
	private GPXtrkpt point2 = new GPXtrkpt(0,2,testDate);
	private GPXtrkpt point3 = new GPXtrkpt(0,3,testDate);
	private GPXtrkpt point4 = new GPXtrkpt(4,0,testDate);
	private GPXtrkpt point5 = new GPXtrkpt(-19,100,testDate);
	private GPXtrkpt point6 = new GPXtrkpt(-10,140,testDate);
	private GPXtrkpt pointoverlat = new GPXtrkpt(-91,0,testDate);
	private GPXtrkpt pointoverlon = new GPXtrkpt(0,190,testDate);
	private GPXtrkpt pointoverboth = new GPXtrkpt(95,185,testDate);
	private GPXtrkpt pointnull;
	
	private ArrayList<GPXtrkseg> seglist = new ArrayList<GPXtrkseg>();
	private ArrayList<GPXtrkpt> pointlist1 = new ArrayList<GPXtrkpt>();
	private ArrayList<GPXtrkpt> pointlist2 = new ArrayList<GPXtrkpt>();
	private ArrayList<GPXtrkpt> pointlist3 = new ArrayList<GPXtrkpt>();
	private ArrayList<GPXtrkpt> pointlist4 = new ArrayList<GPXtrkpt>();
	
	private GPXtrk nulltrack;
	private GPXtrkseg nullseg;
	
	
	
	@Test
	public void testCanFindDistance() {
		//Test that the calculator can find the right distance. 
		//testseg1's distance is 1, testseg2's distance is 10, and testseg3's distance is 41
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		GPXtrkpt extrapoint = new GPXtrkpt(8,3,testDate);
		
		pointlist1.add(point1); pointlist1.add(point2);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);
		
		pointlist2.add(point3); pointlist2.add(point4); pointlist2.add(extrapoint);
		GPXtrkseg testseg2 = new GPXtrkseg(pointlist2);
		
		pointlist3.add(point5); pointlist3.add(point6);
		GPXtrkseg testseg3 = new GPXtrkseg(pointlist3);
		
		seglist.add(testseg1); seglist.add(testseg2);seglist.add(testseg3);
		
		
		 GPXtrk track1 = new GPXtrk("track1", seglist);
		 
		
		assertEquals((double)52, GPXcalculator.calculateDistanceTraveled(track1), 0.0);
	}


	@Test
	public void testNullTrack() {
		//Test the a null track entry returns -1
		
		assertEquals((double)-1, GPXcalculator.calculateDistanceTraveled(nulltrack), 0.0);
	}
	

	@Test
	public void testEmptyTrack() {
		// Test that and empty track returns -1
		seglist.clear();
		
		GPXtrk empty = new GPXtrk("empty", seglist);

		assertEquals((double)-1, GPXcalculator.calculateDistanceTraveled(empty), 0.0);
	}
	

	@Test
	public void testDontCountNullSeg() {
		
		//Make sure null segments don't contribute to distance
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		pointlist1.add(point3); pointlist1.add(point4);
		GPXtrkseg testseg2 = new GPXtrkseg(pointlist1);
		
		pointlist2.add(point5); pointlist2.add(point6);
		GPXtrkseg testseg3 = new GPXtrkseg(pointlist2);
		
		seglist.clear();
		seglist.add(testseg2);seglist.add(testseg3); seglist.add(nullseg);
		
		GPXtrk track1 = new GPXtrk("track1", seglist);

		assertEquals((double)46, GPXcalculator.calculateDistanceTraveled(track1), 0.0);
	}

	@Test
	public void testDontCountPointless() {
		//Test that a segment with no points doesn't add to distance
		
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		pointlist1.add(point1); pointlist1.add(point2);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);
		
		pointlist2.add(point3); pointlist2.add(point4);
		GPXtrkseg testseg2 = new GPXtrkseg(pointlist2);

		GPXtrkseg testseg3 = new GPXtrkseg(pointlist3);

		seglist.clear();
		seglist.add(testseg1); seglist.add(testseg2);seglist.add(testseg3);
	
		
		 GPXtrk track1 = new GPXtrk("track1", seglist);
		
		assertEquals((double)6, GPXcalculator.calculateDistanceTraveled(track1), 0.0);
	}
	
	@Test
	public void testOnePoint() {
		//Test segments with one point have no distance
		
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		pointlist1.add(point1); pointlist1.add(point2);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);
		
		pointlist2.add(point3);
		GPXtrkseg testseg2 = new GPXtrkseg(pointlist2);
		
		pointlist3.add(point5); pointlist3.add(point6);
		GPXtrkseg testseg3 = new GPXtrkseg(pointlist3);

		seglist.clear();
		seglist.add(testseg1); seglist.add(testseg2);seglist.add(testseg3);
		
		GPXtrk track1 = new GPXtrk("track1", seglist);
		
		assertEquals((double)42, GPXcalculator.calculateDistanceTraveled(track1), 0.0);
	}
	
	@Test
	public void testNullPoint() {
		//Test that segments with null points don't contribute distance
		
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		pointlist1.add(point1); pointlist1.add(point2);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);
		
		pointlist2.add(point3);pointlist2.add(point4);
		GPXtrkseg testseg2 = new GPXtrkseg(pointlist2);
		
		pointlist3.add(point5); pointlist3.add(point6); pointlist3.add(pointnull);
		GPXtrkseg testseg3 = new GPXtrkseg(pointlist3);

		seglist.clear();
		seglist.add(testseg1); seglist.add(testseg2);seglist.add(testseg3);
		
		GPXtrk track1 = new GPXtrk("track1", seglist);
		
		assertEquals((double)6, GPXcalculator.calculateDistanceTraveled(track1),0.0);
	}
	
	@Test
	public void testLatTooLarge() {
		//Test that segments that have points outside the latitude boundaries don't have a distance
		
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		pointlist1.add(pointoverlat); pointlist1.add(point2);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);

		pointlist2.add(point5); pointlist2.add(point6);
		GPXtrkseg testseg3 = new GPXtrkseg(pointlist2);

		
		seglist.clear();
		seglist.add(testseg1);seglist.add(testseg3);
		
		GPXtrk track1 = new GPXtrk("track1", seglist);
		
		assertEquals((double)46, GPXcalculator.calculateDistanceTraveled(track1),0.0);
	}
	
	@Test
	public void testLongTooLarge() {
		//Test that segments that have points outside of the boundaries for longitude have 0 distance
		
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		pointlist1.add(point1); pointlist1.add(point2);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);
		
		pointlist2.add(point3); pointlist2.add(pointoverlon);
		GPXtrkseg testseg2 = new GPXtrkseg(pointlist2);
		
		pointlist3.add(point5); pointlist3.add(point6);
		GPXtrkseg testseg3 = new GPXtrkseg(pointlist3);
		
		seglist.clear();
		seglist.add(testseg1); seglist.add(testseg2);seglist.add(testseg3);
		
		GPXtrk track1 = new GPXtrk("track1", seglist);
		
		assertEquals((double)42, GPXcalculator.calculateDistanceTraveled(track1),0.0);
	}
	
	@Test
	public void testComboZero() {
		//Test that a mixture of segments with what should be 0 distances combine for a return distance of 0
		
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		pointlist1.add(point1); pointlist1.add(pointnull);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);
		
		pointlist2.add(point3); pointlist2.add(pointoverlon);
		GPXtrkseg testseg2 = new GPXtrkseg(pointlist2);
		
		pointlist3.add(point5); pointlist3.add(pointoverboth);
		GPXtrkseg testseg3 = new GPXtrkseg(pointlist3);
		
		pointlist4.add(point2);
		GPXtrkseg testseg4 = new GPXtrkseg(pointlist4);
		
		
		seglist.clear();
		seglist.add(testseg1); seglist.add(testseg2);seglist.add(testseg3); seglist.add(testseg4);
		
		GPXtrk track1 = new GPXtrk("track1", seglist);
		
		assertEquals((double)0, GPXcalculator.calculateDistanceTraveled(track1), 0.0);
	}
	
	@Test
	public void testSamePointRepeated() {
		//Test that a segment with the same point twice has a distance of 0
		
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		
		pointlist1.add(point1); pointlist1.add(point1);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);

		seglist.clear();
		seglist.add(testseg1);
		
		GPXtrk track1 = new GPXtrk("track1", seglist);
		
		assertEquals((double)0, GPXcalculator.calculateDistanceTraveled(track1), 0.0);
	}
	
	@Test
	public void testOnTheEdge() {
		//Test that segments with points at the edge of the boundaries (but not beyond it) still have a distance
		
		pointlist1.clear();
		pointlist2.clear();
		pointlist3.clear();
		pointlist4.clear();
		GPXtrkpt posedgeoflatlon = new GPXtrkpt(90,180,testDate);
		GPXtrkpt negedgeoflatlon = new GPXtrkpt(-90,-180,testDate);
		
		pointlist1.add(posedgeoflatlon); pointlist1.add(negedgeoflatlon);
		GPXtrkseg testseg1 = new GPXtrkseg(pointlist1);
		
		seglist.clear();
		seglist.add(testseg1); 
		
		GPXtrk track1 = new GPXtrk("track1", seglist);
		
		assertEquals((180* Math.sqrt(5)), GPXcalculator.calculateDistanceTraveled(track1),0.0);
	}

	
}
