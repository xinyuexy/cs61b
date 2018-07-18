package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        assertEquals(arb.fillCount(), 2);
        arb.enqueue(3);
        assertTrue(arb.isFull());
        arb.dequeue();
        assertEquals((int) arb.peek(),2);
    }

    @Test
    public void testIterable() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        for (int a: arb) {
            System.out.println(a);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
