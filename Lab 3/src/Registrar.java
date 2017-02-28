import tester.Tester;

// Assignment 7
// Cumming Connor
// connorc0405
// Liu Changzong
// changzongliu

// represent a course
class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  // the constructor
  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    this.students = new MtList<Student>();
    prof.addCourse(this);
  }

  // help enroll the given Student in this Course
  void enrollHelper(Student s) {
    this.students = new ConsList<Student>(s, this.students);
  }
}

// represent an Instructor
class Instructor {
  String name;
  IList<Course> courses;

  // the constructor
  Instructor(String name) {
    this.name = name;
    this.courses = new MtList<Course>();
  }
  
  // add the given Course to this Instructor's courses list
  public void addCourse(Course course) {
    this.courses = new ConsList<Course>(course, this.courses);
  }

  // determine if the given Student is in one of more of this Instructor's
  // classes
  boolean dejavu(Student s) {
    return this.courses.count(new HaveStudent(s)) > 1;
  }
}

// represent a Student
class Student {
  String name;
  int id;
  IList<Course> courses;

  // the constructor
  Student(String name, int id) {
    this.name = name;
    this.id = id;
    this.courses = new MtList<Course>();
  }

  // enroll this Student in the given Course
  void enroll(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
    c.enrollHelper(this);
  }

  // determine if this Student is in any of the same classes as the given
  // Student
  boolean classmates(Student s) {
    return this.courses.ormap(new HaveStudent(s));
  }
}

// represent a list of T
interface IList<T> {

  // implements boolean operator "or" with a function object
  boolean ormap(IPredicate<T> pred);

  // determine if this IList<T> contains a T that satisfies
  // the given IComparator<T>.
  boolean include(IComparator<T> comp, T t);

  // counts the number of Ts in this IList<T> that satisfy the given
  // IPredicate<T>
  int count(IPredicate<T> pred);
}

// represent an empty list of T
class MtList<T> implements IList<T> {

  // implements boolean operator "or" with a function object
  public boolean ormap(IPredicate<T> pred) {
    return false;
  }

  // determine if this IList<T> contains a T that satisfies
  // the given IComparator<T>.
  public boolean include(IComparator<T> comp, T t) {
    return false;
  }

  // counts the number of Ts in this Mt<T>List that satisfy the given
  // IPredicate<T>
  public int count(IPredicate<T> pred) {
    return 0;
  }

}

// represent a populated list of T
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  // the constructor
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // implements boolean operator "or" with a function object
  public boolean ormap(IPredicate<T> pred) {
    return pred.apply(this.first) || this.rest.ormap(pred);
  }

  // determine if this IList<T> contains a T that satisfies
  // the given IComparator<T>.
  public boolean include(IComparator<T> comp, T t) {
    return comp.apply(this.first, t) || this.rest.include(comp, t);
  }

  // counts the number of Ts in this Mt<T>List that satisfy the given
  // IPredicate<T>
  public int count(IPredicate<T> pred) {
    if (pred.apply(this.first)) {
      return 1 + this.rest.count(pred);
    }
    else {
      return this.rest.count(pred);
    }
  }
}

// represent a predicate
interface IPredicate<T> {
  boolean apply(T t);
}

// function object that determines if a specific student is in a course
class HaveStudent implements IPredicate<Course> {
  Student s;

  // the constructor
  HaveStudent(Student s) {
    this.s = s;
  }

  // determine if a specific student is in a course
  public boolean apply(Course c) {
    return c.students.include(new SameStudent(), this.s);
  }
}

// compare two Ts
interface IComparator<T> {
  boolean apply(T t1, T t2);
}

// function object that determines if two students are the same
class SameStudent implements IComparator<Student> {

  // determine if the two given Students are the same
  public boolean apply(Student s1, Student s2) {
    return s1.id == s2.id;
  }
}

// represent examples of registrar
class ExamplesRegistrar {
  // students
  Student s1 = new Student("Alice", 1);
  Student s2 = new Student("Bob", 2);
  Student s3 = new Student("David", 3);
  Student s4 = new Student("Tippsie", 4);
  Student s5 = new Student("Daniel", 5);

  // lists of students
  IList<Student> mts = new MtList<Student>();
  IList<Student> ls1 = new ConsList<Student>(s1, new ConsList<Student>(s2, mts));
  IList<Student> ls2 = new ConsList<Student>(s1,
      new ConsList<Student>(s2, new ConsList<Student>(s3, mts)));
  IList<Student> ls3 = new ConsList<Student>(s1, new ConsList<Student>(s2,
      new ConsList<Student>(s3, new ConsList<Student>(s4, new ConsList<Student>(s5, mts)))));
  IList<Student> ls4 = new ConsList<Student>(s3,
      new ConsList<Student>(s2, new ConsList<Student>(s1, mts)));

  // instructors
  Instructor i1 = new Instructor("Kevin");
  Instructor i2 = new Instructor("Ben");

  // courses
  Course c1 = new Course("fundies1", i1);
  Course c2 = new Course("fundies2", i2);
  Course c3 = new Course("GameDesign", i1);
  Course c4 = new Course("Math", i2);

  // lists of courses
  IList<Course> mtc = new MtList<Course>();
  IList<Course> lc1 = new ConsList<Course>(c1, new ConsList<Course>(c2, mtc));
  IList<Course> lc2 = new ConsList<Course>(c1, new ConsList<Course>(c3, mtc));
  IList<Course> lc3 = new ConsList<Course>(c1,
      new ConsList<Course>(c2, new ConsList<Course>(c3, new ConsList<Course>(c4, mtc))));
  IList<Course> lc4 = new ConsList<Course>(c1,
      new ConsList<Course>(c2, new ConsList<Course>(c3, mtc)));
  IList<Course> lc5 = new ConsList<Course>(c3, new ConsList<Course>(c4, mtc));

  // enroll all student in courses
  void initialize() {

    // Kevin teches fundies1 and fundies2
    i1.addCourse(c2);
    i1.addCourse(c1);

    // Ben teches GameDesign and Math
    i2.addCourse(c4);
    i2.addCourse(c3);

    // Alice took 3 courses
    s1.enroll(c3);
    s1.enroll(c2);
    s1.enroll(c1);

    // Bob took 2 courses
    s2.enroll(c2);
    s2.enroll(c1);

    // David took 3 courses
    s3.enroll(c3);
    s3.enroll(c2);
    s3.enroll(c1);

    // Daniel took one courses
    s5.enroll(c4);
  }

  // test the method enroll
  void testEnroll(Tester t) {
    this.s1.courses = mtc;
    this.c1.students = mts;
    this.i1.courses = mtc;

    t.checkExpect(s1.courses, mtc);
    t.checkExpect(c1.students, mts);
    t.checkExpect(i1.courses, mtc);

    
     initialize();
     t.checkExpect(c1.students.include(new SameStudent(), s1), true);
  }

  // test the classmates method
  void testClassmates(Tester t) {
    initialize();
    t.checkExpect(s1.classmates(s5), false);
    t.checkExpect(s2.classmates(s1), true);
    t.checkExpect(s3.classmates(s4), false);
  }

  // test the method count
  void testCount(Tester t) {
    initialize();
    t.checkExpect(lc1.count(new HaveStudent(s1)), 2);
    t.checkExpect(mtc.count(new HaveStudent(s1)), 0);
    t.checkExpect(lc1.count(new HaveStudent(s4)), 0);
  }

  // test the dejavu method
  void testDejavu(Tester t) {
    initialize();
    t.checkExpect(i1.dejavu(s1), true);
    t.checkExpect(i1.dejavu(s4), false);
    t.checkExpect(i1.dejavu(s5), false);
  }

  // test the oramp method
  void testOrmap(Tester t) {
    initialize();
    t.checkExpect(lc1.ormap(new HaveStudent(s1)), true);
    t.checkExpect(mtc.ormap(new HaveStudent(s1)), false);
    t.checkExpect(lc2.ormap(new HaveStudent(s4)), false);
    t.checkExpect(lc2.ormap(new HaveStudent(s5)), false);
  }

  // test include method
  void testInclude(Tester t) {
    initialize();
    t.checkExpect(ls1.include(new SameStudent(), s1), true);
    t.checkExpect(ls2.include(new SameStudent(), s5), false);
    t.checkExpect(mts.include(new SameStudent(), s1), false);
    t.checkExpect(ls2.include(new SameStudent(), s4), false);
  }
}