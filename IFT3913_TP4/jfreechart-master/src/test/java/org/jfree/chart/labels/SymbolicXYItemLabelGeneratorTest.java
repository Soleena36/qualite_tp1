/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * -------------------------------------
 * SymbolicXYItemLabelGeneratorTest.java
 * -------------------------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

import org.jfree.chart.TestUtils;
import CloneUtils;
import PublicCloneable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link SymbolicXYItemLabelGenerator} class.
 */
public class SymbolicXYItemLabelGeneratorTest {

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        SymbolicXYItemLabelGenerator g1 = new SymbolicXYItemLabelGenerator();
        SymbolicXYItemLabelGenerator g2 = new SymbolicXYItemLabelGenerator();
        assertEquals(g1, g2);
        assertEquals(g2, g1);
    }

    /**
     * Simple check that hashCode is implemented.
     */
    @Test
    public void testHashCode() {
        SymbolicXYItemLabelGenerator g1
                = new SymbolicXYItemLabelGenerator();
        SymbolicXYItemLabelGenerator g2
                = new SymbolicXYItemLabelGenerator();
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        SymbolicXYItemLabelGenerator g1 = new SymbolicXYItemLabelGenerator();
        SymbolicXYItemLabelGenerator g2 = CloneUtils.clone(g1);
        assertNotSame(g1, g2);
        assertSame(g1.getClass(), g2.getClass());
        assertEquals(g1, g2);
    }

    /**
     * Check to ensure that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        SymbolicXYItemLabelGenerator g1 = new SymbolicXYItemLabelGenerator();
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        SymbolicXYItemLabelGenerator g1 = new SymbolicXYItemLabelGenerator();
        SymbolicXYItemLabelGenerator g2 = TestUtils.serialised(g1);
        assertEquals(g1, g2);
    }

}