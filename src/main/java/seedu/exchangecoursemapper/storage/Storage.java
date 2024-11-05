package seedu.exchangecoursemapper.storage;

import seedu.exchangecoursemapper.courses.Course;
import java.util.List;

public class Storage {

    private final CourseRepository courseRepository;

    public Storage() {
        this.courseRepository = new CourseRepository();
    }

    public Storage(String filePath) {
        this.courseRepository = new CourseRepository(filePath);
    }

    public void addCourse(Course course) {
        courseRepository.addCourse(course);
    }

    public void deleteCourse(int index) {
        courseRepository.deleteCourse(index);
    }

    public Course getCourse(int index) {
        return courseRepository.getCourse(index);
    }

    public List<Course> loadAllCourses() {
        return courseRepository.loadAllCourses();
    }
}
