package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVO;

@Controller
public class GuestbookController {
	
	//필드
	//@Autowired를 쓰려면 GuestbookService 에 @Service 추가해줘야한다
	@Autowired
	private GuestbookService guestbookService;
	
	//생성자
	
	//메소드gs
	
	//메소드일반
	
	//방명록 리스트
	//http://localhost:8888/guestbook4/list
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("GuestbookController.list()");
		
		//service
		//guestbookService 메모리에 올리고 주소를 넣어라
		//GuestbookService guestbookService = new GuestbookService(); 필드에서 선언 @Autowired
		List<GuestbookVO> guestbookList = guestbookService.exeGetGuestbookList();
		
		//*Model
		//DispatcherServlet한테 request의 어트리뷰트 영역에 "gList" 이름으로 guestbookList을 넣어줘
		model.addAttribute("gList", guestbookList);
		
		//*View
		//포워드
		//return "/WEB-INF/views/addList.jsp";
		return "addList";
	}
	
	
	//방명록 글 저장
	//http://localhost:8888/guestbook4/add?name=aaa&password=123&content=abcde
	//http://localhost:8888/guestbook4/add?id=5&name=aaa&password=123&content=abcde
	@RequestMapping(value="/add", method= {RequestMethod.GET, RequestMethod.POST})
	public String add(@ModelAttribute GuestbookVO guestbookVO)
	{
		System.out.println("add");
		
		/* DispatcherServlet 이 하는 일
		1.request의 파라미터 값을 꺼낸다
			name=aaa	setName()
		2.GuestbookVO 메모리에 올린다
			GuestbookVO guestbookVO = new GuestbookVO();
		3.setter로 값을 넣는다
			name=aaa	setName(aaa)
		-> add(guestbookVO)메소드의 인자값으로 넘긴다
		4. D.s는 add(guestbookVO)
		
		*/
		
		//GuestbookService guestbookService = new GuestbookService();
		guestbookService.exeGuestbookAdd(guestbookVO);
		
		//return "redirect:http://localhost:8888/guestbook4/list"; // 주소 생략 가능
		return "redirect:/list";
		//return "";
	}
	
	
	//방명록 글 저장(위의 방식으로 하도록)
	//http://localhost:8888/guestbook4/add?name=aaa&password=123&content=abcde
	@RequestMapping(value="/add2", method= {RequestMethod.GET, RequestMethod.POST})
	public String add2(@RequestParam(value="name") 		String name,
					   @RequestParam(value="password") 	String password,
					   @RequestParam(value="content") 	String content)
	{
		System.out.println("add2");
		System.out.println("GuestbookController.add()");
		System.out.println("name: "    +name);
		System.out.println("password: "+password);
		System.out.println("content: " +content);
		
		GuestbookVO guestbookVO = new GuestbookVO();
		guestbookVO.setName(name);
		guestbookVO.setPassword(password);
		guestbookVO.setContent(content);
		
		System.out.println(guestbookVO);
		
		//return "/WEB-INF/views/addList.jsp";
		return "addList";
	}
	
	//삭제폼
	//http://localhost:8888/guestbook4/rform
	@RequestMapping(value="/rform", method= {RequestMethod.GET, RequestMethod.POST})
	public String removeForm() {
		System.out.println("GuestbookController.removeForm()");
		
		//return "/WEB-INF/views/removeForm.jsp";
		return "removeForm";
	}
	
	//삭제
	//http://localhost:8888/guestbook4/remove?no=0&password=123
	@RequestMapping(value="/remove", method= {RequestMethod.GET, RequestMethod.POST})
	public String remove(@ModelAttribute GuestbookVO guestbookVO) {
		System.out.println("GuestbookController.remove()");
		System.out.println(guestbookVO);
		
		//GuestbookService guestbookService = new GuestbookService();
		int count = guestbookService.exeGuestbookRemove(guestbookVO);
		
		System.out.println(count);
		
		return "redirect:/list";
	}
}
