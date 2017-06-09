/*
 * Copyright (C) 2014 IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.jpe.web.utils;

import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import fr.ird.driver.eva.business.Specie;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 30 oct. 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public class ColorUtils {

    public final static List<HexColor> YFT_COLORS = getYFTColors();    // new HexColor("#ffff00"), new HexColor("#f5f598"), 12);
    public final static List<HexColor> BFT_COLORS = getBFTColors();    // new HexColor("#009EE0"), new HexColor("#ccebf8"), 8);
    public final static List<HexColor> SKJ_COLORS = getSKJColors();    // new HexColor("#ff0000"), new HexColor("#ffcccc"), 7);
    public final static List<HexColor> BET_COLORS = getBETColors();    // new HexColor("#56B278"), new HexColor("#eef7f1"), 8);
    public final static List<HexColor> FRI_COLORS = getFRIColors();    // new HexColor("#ffa500"), new HexColor("#ffedcc"), 9);
    public final static List<HexColor> ALB_COLORS = getALBColors();    // new HexColor("#D8D900"), new HexColor("#f7f7cc"), 9);

//  public final static List<HexColor> YFT_COLORS = getTintsColors(new HexColor("#511c65"), new HexColor("#dcd1e0"), 12);
////  public final static List<HexColor> YFT_COLORS = getTintsColors(new HexColor("#ffff00"), new HexColor("#f5f598"), 12);
//
//  public final static List<HexColor> BFT_COLORS = getTintsColors(new HexColor("#009EE0"), new HexColor("#ccebf8"), 8);
//  public final static List<HexColor> SKJ_COLORS = getTintsColors(new HexColor("#ff0000"), new HexColor("#ffcccc"), 7);
//  public final static List<HexColor> BET_COLORS = getTintsColors(new HexColor("#56B278"), new HexColor("#eef7f1"), 8);
//  public final static List<HexColor> FRI_COLORS = getTintsColors(new HexColor("#CC8400"), new HexColor("#e5c17f"), 9);
//  public final static List<HexColor> ALB_COLORS = getTintsColors(new HexColor("#D8D900"), new HexColor("#f7f7cc"), 9);
    public static HexColor luminance(HexColor hc, float lum) {
        String rgb = hc.getHexColor();

        if (rgb.startsWith("#")) {
            rgb = rgb.substring(1);
        }

        if (rgb.length() < 6) {
            rgb = String.format("%s%s%s%s%s%s", rgb.charAt(0), rgb.charAt(0), rgb.charAt(1), rgb.charAt(1),
                    rgb.charAt(2), rgb.charAt(2));
        }

//      System.out.println("-----------> " + rgb);
        int red = Integer.parseInt(rgb.substring(0, 2), 16);
        int green = Integer.parseInt(rgb.substring(2, 4), 16);
        int blue = Integer.parseInt(rgb.substring(4, 6), 16);
        HexColor newColor;

        lum = 1 - lum;

//
        newColor = new HexColor(String.format("#%02x%02x%02x",
                Math.round(Math.min(Math.max(0, red + (red * lum)), 255)),
                Math.round(Math.min(Math.max(0, green + (green * lum)), 255)),
                Math.round(Math.min(Math.max(0, blue + (blue * lum)), 255))));

//      newColor = new HexColor(String.format("#%02x%02x%02x", Math.round(red + ((255 - red) * lum)),
//              Math.round(green + ((255 - green) * lum)),
//              Math.round(blue + ((255 - blue) * lum))
//      ));
        return newColor;
    }

    public static HexColor randomColor() {
        Random random = new Random();
        final float hue = random.nextFloat();

//      Saturation between 0.1 and 0.3
        final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        final Color color = Color.getHSBColor(hue, saturation, luminance);

//      System.out.println(color);
        return new HexColor(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
    }

    public static HexColor getColorFromSpecie(String specie) {
        if(specie==null){
            return randomColor();
        }
        int size = 0;

        if (specie.length() == 5) {
            size = Integer.parseInt(specie.substring(4, 5));
            specie = specie.substring(0, 3);
        }

        if (Specie.YELLOWFIN_TUNA.equals(specie)) {
            return YFT_COLORS.get(size);
        } else if (Specie.BLUEFIN_TUNA.equals(specie)) {
            return BFT_COLORS.get(size);
        } else if (Specie.BIGEYE_TUNA.equals(specie)) {
            return BET_COLORS.get(size);
        } else if (Specie.ALBACORE_TUNA.equals(specie)) {
            return ALB_COLORS.get(size);
        } else if (Specie.AUXIDE_TUNA.equals(specie)) {
            return FRI_COLORS.get(size);
        } else if (Specie.SKIPJACK_TUNA.equals(specie)) {
            return SKJ_COLORS.get(size);
        }

        HexColor r = randomColor();

//      while (r.equals(YFT_COLOR) || r.equals(BFT_COLOR) || r.equals(SKJ_COLOR)
//              || r.equals(ALB_COLOR)
//              || r.equals(FRI_COLOR)) {
//          r = randomColor();
//      }
        return r;
    }

    private static List<HexColor> getTintsColors(HexColor f, HexColor l, int n) {
        ArrayList list = new ArrayList();

        list.add(f);

        for (int i = 1; i < n; i++) {
            list.add(new HexColor(tint(n * 2, i * 2, f.getHexColor(), l.getHexColor())));
        }

        list.add(l);

//      System.out.println("list tints color length " + list.size());
        return list;
    }

    private static String tint(int levelNumber, int currentLevel, String couleur1, String couleur2) {
        couleur1 = couleur1.substring(1);
        couleur2 = couleur2.substring(1);

        int R1 = Integer.parseInt(couleur1.substring(0, 2), 16);
        int G1 = Integer.parseInt(couleur1.substring(2, 4), 16);
        int B1 = Integer.parseInt(couleur1.substring(4, 6), 16);
        int pR = (Integer.parseInt(couleur2.substring(0, 2), 16) - R1) / levelNumber;
        int pG = (Integer.parseInt(couleur2.substring(2, 4), 16) - G1) / levelNumber;
        int pB = (Integer.parseInt(couleur2.substring(4, 6), 16) - B1) / levelNumber;
        String valHex = "0123456789ABCDEF";
        String R = "" + valHex.charAt((R1 + pR * currentLevel) / 16)
                + valHex.charAt((R1 + pR * currentLevel) % 16);
        String G = "" + valHex.charAt((G1 + pG * currentLevel) / 16)
                + valHex.charAt((G1 + pG * currentLevel) % 16);
        String B = "" + valHex.charAt((B1 + pB * currentLevel) / 16)
                + valHex.charAt((B1 + pB * currentLevel) % 16);
        String res = "#" + R + G + B;

//      System.out.println(res);
        return res;
    }

    private static List<HexColor> getYFTColors() {
        List<HexColor> colors = new ArrayList<>();

        colors.add(new HexColor("#401650"));
        colors.add(new HexColor("#48195a"));
        colors.add(new HexColor("#511c65"));
        colors.add(new HexColor("#623274"));
        colors.add(new HexColor("#734983"));
        colors.add(new HexColor("#856093"));
        colors.add(new HexColor("#9676a2"));
        colors.add(new HexColor("#a88db2"));
        colors.add(new HexColor("#b9a4c1"));
        colors.add(new HexColor("#cabad0"));
        colors.add(new HexColor("#dcd1e0"));
        colors.add(new HexColor("#ede8ef"));
        colors.add(new HexColor("#401650"));

        return colors;
    }

    private static List<HexColor> getBFTColors() {
        List<HexColor> colors = new ArrayList<>();

        colors.add(new HexColor("#009EE0"));
        colors.add(new HexColor("#19a7e3"));
        colors.add(new HexColor("#32b1e6"));
        colors.add(new HexColor("#4cbbe9"));
        colors.add(new HexColor("#66c4ec"));
        colors.add(new HexColor("#7fceef"));
        colors.add(new HexColor("#99d8f2"));
        colors.add(new HexColor("#b2e1f5"));
        colors.add(new HexColor("#ccebf8"));
        colors.add(new HexColor("#009EE0"));

        return colors;
    }

    private static List<HexColor> getSKJColors() {
        List<HexColor> colors = new ArrayList<>();

        colors.add(new HexColor("#ff1919"));
        colors.add(new HexColor("#ff2f2f"));
        colors.add(new HexColor("#ff4646"));
        colors.add(new HexColor("#ff5e5e"));
        colors.add(new HexColor("#ff7575"));
        colors.add(new HexColor("#ff8c8c"));
        colors.add(new HexColor("#ffa3a3"));
        colors.add(new HexColor("#ffbaba"));
        colors.add(new HexColor("#ffd1d1"));
        colors.add(new HexColor("#ff1919"));

        return colors;
    }

    private static List<HexColor> getBETColors() {
        List<HexColor> colors = new ArrayList<>();

        colors.add(new HexColor("#56b278"));
        colors.add(new HexColor("#66b985"));
        colors.add(new HexColor("#77c193"));
        colors.add(new HexColor("#88c9a0"));
        colors.add(new HexColor("#99d0ae"));
        colors.add(new HexColor("#aad8bb"));
        colors.add(new HexColor("#bbe0c9"));
        colors.add(new HexColor("#cce7d6"));
        colors.add(new HexColor("#ddefe4"));
        colors.add(new HexColor("#56b278"));

        return colors;
    }

    private static List<HexColor> getALBColors() {
        List<HexColor> colors = new ArrayList<>();

        colors.add(new HexColor("#D8D900"));
        colors.add(new HexColor("#dbdc19"));
        colors.add(new HexColor("#dfe032"));
        colors.add(new HexColor("#e3e44c"));
        colors.add(new HexColor("#e7e866"));
        colors.add(new HexColor("#ebec7f"));
        colors.add(new HexColor("#efef99"));
        colors.add(new HexColor("#f3f3b2"));
        colors.add(new HexColor("#f7f7cc"));
        colors.add(new HexColor("#D8D900"));

        return colors;
    }

    private static List<HexColor> getFRIColors() {
        List<HexColor> colors = new ArrayList<>();

        colors.add(new HexColor("#ffa500"));
        colors.add(new HexColor("#ffae19"));
        colors.add(new HexColor("#ffb732"));
        colors.add(new HexColor("#ffc04c"));
        colors.add(new HexColor("#ffc966"));
        colors.add(new HexColor("#ffd27f"));
        colors.add(new HexColor("#ffdb99"));
        colors.add(new HexColor("#ffe4b2"));
        colors.add(new HexColor("#ffedcc"));
        colors.add(new HexColor("#ffa500"));

        return colors;
    }
}
