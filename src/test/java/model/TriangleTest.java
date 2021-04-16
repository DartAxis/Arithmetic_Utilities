package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    @Test
    void TestProcessTriangle(){
        Triangle triangle = new Triangle(3d,5d,4d);
        assertEquals(3d,triangle.getSideAB());
        assertEquals(5d,triangle.getSideBC());
        assertEquals(4d,triangle.getSideAC());
        System.out.println(triangle.getDecision());
    }
}