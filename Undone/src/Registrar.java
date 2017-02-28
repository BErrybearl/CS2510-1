interface IRegistrar {

}

class Course implements IRegistrar {
  String name;
  Instructor prof;
  IList<Student> students;

  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    this.students = new MtList<Student>();

  }

  void enrollHelper(Student s) {
    this.students = new ConsList<Student>(s, this.students);
  }

  void techHelper(Instructor prof) {
    this.prof = prof;
  }
}

class Instructor implements IRegistrar {
  String name;
  IList<Course> courses;

  Instructor(String name) {
    this.name = name;
    this.courses = new MtList<Course>();
  }

  boolean dejavu(Student s) {
    return this.courses.count(new HaveStudent(s)) > 1;
  }

  void tech(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
    c.techHelper(this);
  }

}

class Student implements IRegistrar {
  String name;
  int id;
  IList<Course> courses;

  Student(String name, int id) {
    this.name = name;
    this.id = id;
    this.courses = new MtList<Course>();
  }

  void enroll(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
    c.enrollHelper(this);
  }

  boolean classmates(Student s) {
    return this.courses.ormap(new HaveStudent(s));
  }

}

interface IList<T> {
  boolean ormap(IPredicate<T> pred);

  boolean include(IComparator<T> comp, T t);

  int count(IPredicate<T> pred);
}

class MtList<T> implements IList<T> {
  public boolean include(IComparator<T> comp, T t) {
    return false;
  }

  public boolean ormap(IPredicate<T> pred) {
    return false;
  }

  public int count(IPredicate<T> pred) {
    return 0;
  }

}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean include(IComparator<T> comp, T t) {
    return comp.apply(this.first, t) || this.rest.include(comp, t);
  }

  public boolean ormap(IPredicate<T> pred) {
    return pred.apply(this.first) || this.rest.ormap(pred);
  }

  public int count(IPredicate<T> pred) {  
    if (pred.apply(this.first)) {
      return 1 + this.rest.count(pred);
    }
    else {
      return this.rest.count(pred);
    }
  }

}

interface IPredicate<T> {
  boolean apply(T t);
}

class HaveStudent implements IPredicate<Course> {
  Student s;

  HaveStudent(Student s) {
    this.s = s;
  }

  public boolean apply(Course c) {
    return c.students.include(new SameStudent(), this.s);
  }
}

interface IComparator<T> {
  boolean apply(T t1, T t2);
}

class SameStudent implements IComparator<Student> {

  public boolean apply(Student s1, Student s2) {
    return s1.id == s2.id;
  }
}