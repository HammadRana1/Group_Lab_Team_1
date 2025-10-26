package Business.University.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDirectory {
    private List<Department> departmentList;

    public DepartmentDirectory() {
        departmentList = new ArrayList<>();
    }

    public Department newDepartment(String name) {
        Department dept = new Department(name);
        departmentList.add(dept);
        return dept;
    }

    public void addDepartment(Department dept) {
        departmentList.add(dept);
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }
}
