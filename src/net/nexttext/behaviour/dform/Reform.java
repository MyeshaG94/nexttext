/*
  This file is part of the NextText project.
  http://www.nexttext.net/

  Copyright (c) 2004-08 Obx Labs / Jason Lewis

  NextText is free software: you can redistribute it and/or modify it under
  the terms of the GNU General Public License as published by the Free Software 
  Foundation, either version 2 of the License, or (at your option) any later 
  version.

  NextText is distributed in the hope that it will be useful, but WITHOUT ANY
  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
  A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with 
  NextText.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.nexttext.behaviour.dform;

import net.nexttext.TextObjectGlyph;
import net.nexttext.Vector3;
import net.nexttext.property.Vector3PropertyList;
import net.nexttext.property.Vector3Property;

import java.util.Iterator;

/**
 * A DForm which reverts TextObject to its original shape.
 *
 * <p>Different ways of reforming the glyphs are provided, which given
 * different visual effects.  </p>
 * 
 * <p>The current ActionResult returned specifies that a Reform action never
 * terminates, it sends a true event once it's reformed. We probably want 
 * to change it so that it can terminate. If needs be, the Reform could be put
 * into a Repeat behaviour. </p>
 */
/* $Id$ */
public class Reform extends DForm {
    
    public static final int STYLE_LINEAR = 1;
    public static final int STYLE_EXPONENTIAL = 2;    
    
    int style = STYLE_LINEAR;
    
    double linearSpeed = 0.05;
    double exponentialSpeed = 2000;

    /**
     * Linear style of reformation doesn't deform glyph shape.
     */
    public void setStyleLinear() {
        style = STYLE_LINEAR;
    }

    /**
     * Exponential style reformation preserves deformations longer.
     */
    public void setStyleExponential() {
        style = STYLE_EXPONENTIAL;
    }

    /**
     * Constructs a default Reform of linear style with a default speed of 0.05.
     */
    public Reform() {
    }
    
    /**
     * Constructs a custom Reform with given style and appropriate speed.
     *
     * <p>In exponential style, smaller values give faster reforms, the default 
     * value is 2000.</p>
     *
     * <p>In linear style, smaller values give slower reforms, the default 
     * value is 0.05</p>.
     *
     * @param speed the speed value according to the chosen style
     * @param style the type of reformation (linear or exponential)
     */
    public Reform(double speed, int style) {
        
        if (style == STYLE_LINEAR) {
            this.style = STYLE_LINEAR;
            linearSpeed = speed;
            
        } else if (style == STYLE_EXPONENTIAL) {
            this.style = STYLE_EXPONENTIAL;
            exponentialSpeed = speed;
            
        }
    }

    public ActionResult behave(TextObjectGlyph to) {         
        // Traverse the control points of the glyph, determine the distance
        // from its current location to the origin and move it part way there.
        Vector3PropertyList cPs = getControlPoints(to);
        Iterator<Vector3Property> i = cPs.iterator();
        
        boolean done = true;
        
        // if the glyph is not deformed, don't waste time reforming it
        if (!to.isDeformed())
            return new ActionResult(false, false, true);
        
        while (i.hasNext()) {
            Vector3Property cP = i.next();
            Vector3 cV = cP.get();
            Vector3 oV = cP.getOriginal();

            Vector3 offset = new Vector3(oV);
            offset.sub(cV);

            // In order not to produce gratuitous property change events, if
            // the offset is short, nothing is done.
            if (offset.length() < 0.1) continue;

            // The reform algorithm is very slow when the points are close, so
            // once we reach a distance of 0.8 we just snap it back to its
            // original.

            if (offset.length() > 0.8) {
                done = false;

                if (style == STYLE_EXPONENTIAL) {                    
                    offset.scalar(1 - Math.pow(Math.E, - offset.length()/exponentialSpeed));
                } else {
                    offset.scalar(linearSpeed);
                }
            }            
            cV.add(offset);
            cP.set(cV);
        }
        if ( done ) {
            to.setDeformed(false);
            return new ActionResult(false, false, true);
        }
        return new ActionResult(false, false, false);
    }

    public double getExponentialSpeed() {
        return exponentialSpeed;
    }

    /**
     * Sets the speed of the reform when using the exponential style.
     * @param exponentialSpeed an appropriate speed for exponential style
     */
    public void setExponentialSpeed(double exponentialSpeed) {
        this.exponentialSpeed = exponentialSpeed;
    }

    public double getLinearSpeed() {
        return linearSpeed;
    }

    /**
     * Sets the speed of the reform when using the linear style.
     * @param linearSpeed an appropriate speed for linear style
     */
    public void setLinearSpeed(double linearSpeed) {
        this.linearSpeed = linearSpeed;
    }
}
