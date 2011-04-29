/**
 * 
 */
package pt.ua.sd.ocean;

import java.awt.Color;
import java.awt.Point;

import pt.ua.gboard.FilledGelem;
import pt.ua.gboard.GBoard;
import pt.ua.gboard.Gelem;
import pt.ua.gboard.ImageGelem;
import pt.ua.sd.boat.BoatId;
import pt.ua.sd.shoal.ShoalId;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class FishingGBoard {

	protected final GBoard gmap;
	protected final int sizeFactor; // gmap height and width will be factorTimes
									// the map's height and width
	protected final int backgroundLayer = 0;
	protected final int moverLayer = 1;
	protected final int gmapLayers = 2;
	protected final Gelem backgroundGelem;
	protected final String boatIcons[] = { "res/boat_g.png", "res/boat_p.png", "res/boat_r.png", "res/boat_y.png",
			"res/boat_b.png" };
	protected final String fishIcon = "res/fish.png";
	protected final Gelem boatGelems[] = new Gelem[boatIcons.length];
	protected final Gelem shoalGelem;

	protected Object map[][];

	/**
	 * Build a new graphic map of width vs height cells. The map handles movers.
	 * All movers are draw in the same layer.
	 * 
	 * sizeFactor must guarantee that it is possible to draw sizeFactor *
	 * sizeFactor movers in a cell position. Only BoatId and ShoalId are
	 * supported, given their getCompany and getShoal methods, to determine the
	 * best gelem to use.
	 * 
	 * If a cell is full, the mover will not be drawn. This causes no problems
	 * when removing.
	 * 
	 * Make sure to implement movers .equals() method.
	 * 
	 * @param width
	 * @param height
	 * @param sizeFactor
	 */
	public FishingGBoard(int width, int height, int sizeFactor) {
		this.sizeFactor = sizeFactor;

		map = new Object[height * sizeFactor][width * sizeFactor];

		// graphic stuff
		gmap = new GBoard("Pescaria", height * sizeFactor, width * sizeFactor, 50 / sizeFactor, 50 / sizeFactor,
				gmapLayers);
		// backgroundGelem = new ImageGelem("res/water.jpg", gmap, 100,
		// height*sizeFactor, width*sizeFactor);
		backgroundGelem = new FilledGelem(Color.blue, 95, sizeFactor, sizeFactor);

		for (int r = 0; r < height; r++) {
			for (int s = 0; s < width; s++) {
				gmap.draw(backgroundGelem, r * sizeFactor, s * sizeFactor, backgroundLayer);
			}
		}

		for (int r = 0; r < boatGelems.length; r++) {
			boatGelems[r] = new ImageGelem(boatIcons[r], gmap, 80);
		}

		shoalGelem = new ImageGelem("res/fish.png", gmap, 80);
	}

	synchronized public void draw(BoatId id, Point p) {
		assert isValid(p);

		int y = p.y * sizeFactor, x = p.x * sizeFactor;
		for (int r = 0; r < sizeFactor; r++) {
			for (int c = 0; c < sizeFactor; c++) {
				if (map[y + r][x + c] == null) {
					gmap.draw(boatGelems[id.getCompany() % boatGelems.length], y + r, x + c, moverLayer);
					map[y + r][x + c] = id;
					return;
				}
			}
		}
	}

	synchronized public void erase(BoatId id, Point p) {
		assert isValid(p);

		int y = p.y * sizeFactor, x = p.x * sizeFactor;
		for (int r = 0; r < sizeFactor; r++) {
			for (int c = 0; c < sizeFactor; c++) {
				if (map[y + r][x + c] != null && map[y + r][x + c].equals(id)) {
					gmap.erase(boatGelems[id.getCompany() % boatGelems.length], y + r, x + c, moverLayer);
					map[y + r][x + c] = null;
					return;
				}
			}
		}
	}

	synchronized public void draw(ShoalId id, Point p) {
		assert isValid(p);

		int y = p.y * sizeFactor, x = p.x * sizeFactor;
		for (int r = 0; r < sizeFactor; r++) {
			for (int c = 0; c < sizeFactor; c++) {
				if (map[y + r][x + c] == null) {
					gmap.draw(shoalGelem, y + r, x + c, moverLayer);
					map[y + r][x + c] = id;
					return;
				}
			}
		}
	}

	synchronized public void erase(ShoalId id, Point p) {
		assert isValid(p);

		int y = p.y * sizeFactor, x = p.x * sizeFactor;
		for (int r = 0; r < sizeFactor; r++) {
			for (int c = 0; c < sizeFactor; c++) {
				if (map[y + r][x + c] != null && map[y + r][x + c].equals(id)) {
					gmap.erase(shoalGelem, y + r, x + c, moverLayer);
					map[y + r][x + c] = null;
					return;
				}
			}
		}
	}

	protected boolean isValid(Point p) {
		return p.y >= 0 && p.y < gmap.numberOfLines() && p.x >= 0 && p.x < gmap.numberOfColumns();
	}
}
