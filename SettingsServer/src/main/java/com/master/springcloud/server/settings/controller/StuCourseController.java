package com.master.springcloud.server.settings.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.master.springcloud.server.settings.commons.enity.Constant;
import com.master.springcloud.server.settings.commons.enity.ReturnObject;
import com.master.springcloud.server.settings.domain.CourseStudent;
import com.master.springcloud.server.settings.domain.StuCourse;
import com.master.springcloud.server.settings.domain.StudentCourse;
import com.master.springcloud.server.settings.domain.StudentState;
import com.master.springcloud.server.settings.service.StuCourseService;
import com.master.springcloud.server.settings.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workbench/stuCourse")
@RequiredArgsConstructor
public class StuCourseController {

    final private StuCourseService stuCourseService;
    final private StudentService studentService;

    @RequestMapping("/save")
    public Boolean saveStuCourse(StuCourse stuCourse) {
        stuCourse.setId(UUID.randomUUID().toString(true));
        stuCourse.setScore(RandomUtil.randomInt(0, 100));
        stuCourse.setExamDate(RandomUtil.randomDate(new DateTime(), DateField.DAY_OF_YEAR,0,20).toDateStr());
        return stuCourseService.saveStuCourse(stuCourse);
    }

    @RequestMapping("/queryList")
    public Object queryList(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum, @RequestParam("stuId") String stuId) {
        ReturnObject returnObject = new ReturnObject();
        try {
            List<StudentCourse> studentCourses = stuCourseService.queryList(pageSize, pageNum, stuId);
            int length = stuCourseService.queryNum(stuId);
            Map<String, Object> map = new HashMap<>(2);
            map.put("data",studentCourses);
            map.put("length",length);
            returnObject.setData(map);
            returnObject.setCode(Constant.SUCCESS);
            returnObject.setMessage("????????????");
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("????????????");
        }
        return returnObject;
    }

    @RequestMapping("/queryListOK")
    public Object queryListOK(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum, @RequestParam("stuId") String stuId) {
        ReturnObject returnObject = new ReturnObject();
        try {
            List<StudentCourse> studentCourses = stuCourseService.queryListOK(pageSize, pageNum, stuId);
            int length = stuCourseService.queryNumOK(stuId);
            Map<String, Object> map = new HashMap<>(2);
            map.put("data",studentCourses);
            map.put("length",length);
            returnObject.setData(map);
            returnObject.setCode(Constant.SUCCESS);
            returnObject.setMessage("????????????");
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("????????????");
        }
        return returnObject;
    }

    @RequestMapping("/removeStuCourse")
    public ReturnObject removeStuCourse(@RequestParam("id") String id) {
        ReturnObject returnObject = new ReturnObject();
        try {
             Boolean result = stuCourseService.deleteStuCourse(id);
                if (result) {
                    returnObject.setCode(Constant.SUCCESS);
                    returnObject.setMessage("????????????");
                } else {
                    returnObject.setCode(Constant.FAIL);
                    returnObject.setMessage("?????????????????????????????????");
                }

        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("??????????????????????????????");
        }
        return returnObject;
    }

    @GetMapping("/getStudentState")
    public ReturnObject getStudentState(@RequestParam int pageSize, @RequestParam int pageNum) {
        ReturnObject returnObject = new ReturnObject();
        try {
            List<StudentState> studentState = stuCourseService.getStudentState(pageSize, pageNum);
            int length = studentService.queryNum();
            Map<String, Object> map = new HashMap<>(2);
            returnObject.setCode(Constant.SUCCESS);
            returnObject.setMessage("????????????");
            map.put("data",studentState);
            map.put("length",length);
            returnObject.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("????????????");
        }
        return returnObject;
    }
    @GetMapping("/getStudentStateByCondition")
    public ReturnObject getStudentStateByCondition(@RequestParam int pageSize, @RequestParam int pageNum,@RequestParam String studentName,@RequestParam String studentId) {
        ReturnObject returnObject = new ReturnObject();
        try {
            List<StudentState> studentState = stuCourseService.getStudentStateByCondition(pageSize, pageNum,studentId,studentName);
            int length = studentService.queryNumByCondition(studentId, studentName);
            Map<String, Object> map = new HashMap<>(2);
            returnObject.setCode(Constant.SUCCESS);
            returnObject.setMessage("????????????");
            map.put("data",studentState);
            map.put("length",length);
            returnObject.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("????????????");
        }
        return returnObject;
    }

    @GetMapping("/getStudentStateById")
    public ReturnObject getStudentStateByCondition(@RequestParam String studentId) {
        ReturnObject returnObject = new ReturnObject();
        try {
            StudentState studentState = stuCourseService.getStudentStateById(studentId);
            returnObject.setCode(Constant.SUCCESS);
            returnObject.setMessage("????????????");
            returnObject.setData(studentState);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("????????????");
        }
        return returnObject;
    }

    @GetMapping("/getCourseStudent")
    public ReturnObject getCourseStudent(@RequestParam String courseId,@RequestParam int pageSize,@RequestParam int pageNum){
        ReturnObject returnObject = new ReturnObject();
        try {
            List<CourseStudent> courseStudent = stuCourseService.getCourseStudent(courseId, pageNum, pageSize);
            Integer length = stuCourseService.getCourseStudentNum(courseId);
            HashMap<String, Object> map = new HashMap<>(2);
            returnObject.setCode(Constant.SUCCESS);
            returnObject.setMessage("???????????????");
            map.put("list",courseStudent);
            map.put("length",length);
            returnObject.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("??????????????????????????????");
        }
        return returnObject;
    }

    @GetMapping("/getCourseStudentOk")
    public ReturnObject getCourseStudentOk(@RequestParam String courseId,@RequestParam int pageSize,@RequestParam int pageNum){
        ReturnObject returnObject = new ReturnObject();
        try {
            List<CourseStudent> courseStudent = stuCourseService.getCourseStudentOK(courseId, pageNum, pageSize);
            Integer length = stuCourseService.getCourseStudentNumOk(courseId);
            HashMap<String, Object> map = new HashMap<>(2);
            returnObject.setCode(Constant.SUCCESS);
            returnObject.setMessage("???????????????");
            map.put("list",courseStudent);
            map.put("length",length);
            returnObject.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("??????????????????????????????");
        }
        return returnObject;
    }

    @RequestMapping("/updateCourseStudent")
    public ReturnObject updateCourseStudent(@RequestParam String id,@RequestParam int score) {
        ReturnObject returnObject = new ReturnObject();
        try {
            StuCourse stuCourse = stuCourseService.getCourseStudentById(id);
            stuCourse.setScore(score);
            stuCourse.setExamDate(DateTime.now().toString("yyyy-MM-dd"));
            Boolean result = stuCourseService.updateCourseStudent(id,stuCourse);
            if (result) {
                returnObject.setCode(Constant.SUCCESS);
                returnObject.setMessage("????????????");
            } else {
                returnObject.setCode(Constant.FAIL);
                returnObject.setMessage("?????????????????????????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.FAIL);
            returnObject.setMessage("??????????????????????????????");
        }
        return returnObject;
    }

}
