package cl.usach.ingesoft.agendator.entity.base;

import cl.usach.ingesoft.agendator.common.BusinessException;
import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;
import cl.usach.ingesoft.agendator.util.OmitInHashcode;
import cl.usach.ingesoft.agendator.util.OmitInToString;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BaseEntityTest {

    // test Annotations
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface Foo {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface Bar {
    }

    // test clases
    private static class A {
        @Foo
        int x;
        @Foo
        int y;
    }

    private static class B {
        @Foo
        int x;
        @Bar
        int y;
    }

    private static class C {
        @Bar
        int x;
        @Bar
        int y;
    }

    private static class D {
        int x;
        int y;
        @Foo
        @Bar
        int c;
    }

    // test utilitary method
    private static List<String> makeList(String... args) {
        return Arrays.asList(args);
    }

    @Before
    public void setUp() {
        h1 = new H1();
        h2 = new H2();
        h3 = new H3();
        h4 = new H4();
        h5 = new H5();


        s = new S();
        t = new T();
        u1 = new U();
        u2 = new U();
        v1 = new V();
        v2 = new V();

        u1.x = 1;
        u2.x = 2;
        v1.v = 3;
        v2.v = 4;

        w = new W();
        x = new X();
        y = new Y();
        z = new Z();
    }

    @Test
    public void listFieldsWithAnnotationTest() {
        assertEquals(makeList("x", "y"), BaseEntity.listFieldsWithAnnotation(A.class, Foo.class));
        assertEquals(makeList("x"), BaseEntity.listFieldsWithAnnotation(B.class, Foo.class));
        assertEquals(makeList(), BaseEntity.listFieldsWithAnnotation(C.class, Foo.class));

        assertEquals(makeList(), BaseEntity.listFieldsWithAnnotation(A.class, Bar.class));
        assertEquals(makeList("y"), BaseEntity.listFieldsWithAnnotation(B.class, Bar.class));
        assertEquals(makeList("x", "y"), BaseEntity.listFieldsWithAnnotation(C.class, Bar.class));

        assertEquals(makeList("c"), BaseEntity.listFieldsWithAnnotation(D.class, Foo.class));
        assertEquals(makeList("c"), BaseEntity.listFieldsWithAnnotation(D.class, Bar.class));
    }

    private static class H1 extends BaseEntity {
        int x = 1;
    }

    private static class H2 extends BaseEntity {
        int y = 1;
    }

    private static class H3 extends BaseEntity {
        int z = 2;
    }

    private static class H4 extends BaseEntity {
        @OmitInHashcode
        char c = 'a';
    }

    private static class H5 extends BaseEntity {
        @OmitInHashcode
        char c = 'b';
    }

    private H1 h1;
    private H2 h2;
    private H3 h3;
    private H4 h4;
    private H5 h5;

    @Test
    public void hashCodeTest() {
        assertNotNull(h1);
        assertNotNull(h2);
        assertNotNull(h3);
        assertNotNull(h4);
        assertNotNull(h5);

        assertEquals(h1.hashCode(), h1.hashCode());
        assertEquals(h1.hashCode(), h2.hashCode());
        assertNotEquals(h1.hashCode(), h3.hashCode());
        assertEquals(h4.hashCode(), h5.hashCode());
    }

    private class S extends BaseEntity {
    }

    private class T extends BaseEntity {
    }

    private class U extends BaseEntity {
        int x;
    }

    class V extends BaseEntity {
        @OmitInComparison
        int v;
    }

    private S s;
    private T t;
    private U u1, u2;
    private V v1, v2;

    @Test
    public void equalsTest() {
        assertNotNull(s);
        assertNotNull(t);
        assertNotNull(u1);
        assertNotNull(u2);
        assertNotNull(v1);
        assertNotNull(v2);

        assertEquals(new S(), s);
        assertEquals(new T(), t);
        assertNotNull(s);
        assertNotNull(t);
        assertNotEquals(s, t);
        assertNotEquals(t, s);
        assertEquals(u1, u1);
        assertNotEquals(u1, u2);
        assertNotEquals(u1, s);
        assertNotEquals(u1, new U());

        assertNotEquals(v1.v, v2.v);
        assertEquals(v1, v2);

        assertNotEquals(v1, null);
        assertNotEquals(null, v1);
    }

    private static class W extends BaseEntity {
        @OmitInToString
        int w2 = 999;
    }

    private static class X extends BaseEntity {
        int x = 1;
    }

    private static class Y extends BaseEntity {
        String x = "1";
    }

    private static class Z extends BaseEntity {
        Object z = Boolean.FALSE;
    }

    private W w;
    private X x;
    private Y y;
    private Z z;

    @Test
    public void toStringTest() {
        assertNotNull(w);
        assertNotNull(x);
        assertNotNull(y);
        assertNotNull(z);

        assertNotNull(w.toString());
        assertNotNull(x.toString());
        assertNotNull(y.toString());
        assertNotNull(z.toString());
    }

    // test BusinessException

    @Test(expected = BusinessException.class)
    public void exceptionTest() {
        throw new BusinessException("foo & bar");
    }

    @Test
    public void exception2Test() {
        BusinessException e = new BusinessException("foo & bar");
        assertEquals("foo & bar", e.getMessage());

        Throwable t = new Throwable();

        BusinessException f = new BusinessException(t);
        assertEquals(t, f.getCause());

        BusinessException ef = new BusinessException("foo", t);
        assertEquals(t, ef.getCause());
        assertEquals("foo", ef.getMessage());
    }

}
