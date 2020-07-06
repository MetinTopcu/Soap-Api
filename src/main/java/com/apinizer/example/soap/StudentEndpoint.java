package com.apinizer.example.soap;

import com.apinizer.example.xml.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.apinizer.example.xml.StudentDetailsRequest;
import com.apinizer.example.xml.StudentDetailsResponse;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class StudentEndpoint {
	private static final String NAMESPACE_URI = "http://www.apinizer.com/xml/school";

	private StudentRepository StudentRepository;

	@Autowired
	public StudentEndpoint(StudentRepository StudentRepository) {
		this.StudentRepository = StudentRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentDetailsRequest")
	@ResponsePayload
	public StudentDetailsResponse getStudent(@RequestPayload StudentDetailsRequest request) {
		StudentDetailsResponse response = new StudentDetailsResponse();
		response.setStudent(StudentRepository.findStudent(request.getName()));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentDetailsList")
	@ResponsePayload
	public List<StudentDetailsResponse> getStudentList() {
		List<StudentDetailsResponse> response = new ArrayList<>();
		for (int i = 1; i < 11; i++) {
			StudentDetailsResponse st = new StudentDetailsResponse();
			Student student = new Student();
			student.setName("Student_"+i);
			student.setStandard(5+i);
			student.setAddress("Address_"+i);
			st.setStudent(student);
			response.add(st);
		}

		return response;
	}
}