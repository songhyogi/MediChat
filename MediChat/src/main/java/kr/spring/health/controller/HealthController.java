
package kr.spring.health.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.health.service.HealthyService;
import kr.spring.health.vo.HealthyBlogVO;
import kr.spring.health.vo.HealthyReplyVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import kr.spring.video.service.VideoService;
import kr.spring.video.vo.VideoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HealthController {

	@Autowired
	private HealthyService service;

	@Autowired
	private VideoService videoservice;

	@ModelAttribute
	public HealthyBlogVO initCommand() {
		return new HealthyBlogVO();
	}
	//마이페이지 댓글
			@GetMapping("/doctor/healthyMydoc")
			public String getMyDocHeathList(HttpServletRequest request,HttpSession session,@RequestParam(defaultValue="1") int pageNum,Model model) {
				Map<String,Object> map = new HashMap<String,Object>();
				String user_type = (String)session.getAttribute("user_type");
				Object user = (Object) session.getAttribute("user");

				if(user != null) {
					if(user_type != null) {
						DoctorVO duser = (DoctorVO) user;
						map.put("mem_num", duser.getMem_num());
					}else{
						model.addAttribute("message","접근 권한이 없습니다.");
						model.addAttribute("url",request.getContextPath()+"/main/main");
						return "common/resultAlert";
					}
						
					
				}else {
					model.addAttribute("message","로그인 후 사용가능합니다.");
					model.addAttribute("url",request.getContextPath()+"/doctor/login");
					return "common/resultAlert";
				}
				int count = service.selectDocByHealCount(map);
				PagingUtil page = new PagingUtil(pageNum,count,5,10,"healthyMydoc");

				map.put("start", page.getStartRow());
				map.put("end", page.getEndRow());

				List<HealthyBlogVO> list = service.selectDocByHealList(map);

				model.addAttribute("list",list);
				model.addAttribute("count",count);
				model.addAttribute("page",page.getPage());

				
				return"docPagehealthyList";
			}
	//마이페이지 댓글
		@GetMapping("/member/healthyMyreply")
		public String getMyHeathReplyList(HttpServletRequest request,HttpSession session,@RequestParam(defaultValue="1") int pageNum,Model model) {
			Map<String,Object> map = new HashMap<String,Object>();
			String user_type = (String)session.getAttribute("user_type");
			Object user = (Object) session.getAttribute("user");

			if(user != null) {
				
					MemberVO muser = (MemberVO) user;
					map.put("mem_num", muser.getMem_num());
				
			}else {
				model.addAttribute("message","로그인 후 사용가능합니다.");
				model.addAttribute("url",request.getContextPath()+"/member/login");
				return "common/resultAlert";
			}
			int count = service.selectMyHealReCount(map);
			PagingUtil page = new PagingUtil(pageNum,count,5,10,"healthyMyreply");

			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());

			List<HealthyReplyVO> list = service.selectMyReHealList(map);

			model.addAttribute("list",list);
			model.addAttribute("count",count);
			model.addAttribute("page",page.getPage());

			
			return"myReHealthyList";
		}
	//마이페이지
	@GetMapping("/member/healthyMy")
	public String getMyHeathList(HttpServletRequest request,HttpSession session,@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="1") int rpageNum,Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");

		if(user != null) {
			
				MemberVO muser = (MemberVO) user;
				map.put("mem_num", muser.getMem_num());
			
		}else {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			return "common/resultAlert";
		}
		int count = service.selectMyFavHealCount(map);

		PagingUtil page = new PagingUtil(pageNum,count,5,10,"healthyMy");

		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());

		List<HealthyBlogVO> list = service.selectMyFavHealList(map);

		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());

		
		int reFavcount = service.selectMyFavHealReCount(map);
		PagingUtil repage = new PagingUtil(rpageNum,reFavcount,5,10,"healthyMy");
		map.put("start", repage.getStartRow());
		map.put("end", repage.getEndRow());
		List<HealthyReplyVO> relist = service.selectMyFavReHealList(map);
		model.addAttribute("relist",relist);
		model.addAttribute("recount",reFavcount);
		model.addAttribute("repage",repage.getPage());
	
		log.debug("<<<<<<<<<<<<<<<<<<"+ relist);
		return"myFavHealthyList";
	}
	
	@GetMapping("/health/healthBlog")
	public String getHeathList(@RequestParam(defaultValue="1") int pageNum,String keyword, String keyfield,@RequestParam(defaultValue="1") int vpageNum,String vkeyword, String vkeyfield,Model model) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		int count = service.selectHealCount(map);

		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,6,10,"healthyBlog");

		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());

		List<HealthyBlogVO> list = service.selectHealList(map);
		model.addAttribute("keyfield", keyfield);
		model.addAttribute("keyword", keyword);
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());

		map.put("vkeyword", vkeyword);
		map.put("vkeyfield", vkeyfield);
		int vcount = videoservice.selectCountV(map);
		PagingUtil vpage = new PagingUtil(keyfield,keyword,vpageNum,vcount,3,vcount,"healthyBlog");
		map.put("start", vpage.getStartRow());
		map.put("end", vpage.getEndRow());
		List<VideoVO> vlist = videoservice.selectVList(map);
		model.addAttribute("vlist",vlist);
		model.addAttribute("vcount",vcount);
		model.addAttribute("vpage",vpage.getPage());
		model.addAttribute("vpageNum",vpageNum);
		int maxPage =(int) Math.ceil((double)vcount/3);
		model.addAttribute("maxPage", maxPage);
	
		return"healthy_Blog";
	}
	@GetMapping("/health/healthM")
	public String getHeathm(@RequestParam(defaultValue="1") int pageNum,String keyword, String keyfield,Model model) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		int count = service.selectHealCount(map);

		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,6,10,"healthM");

		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());

		List<HealthyBlogVO> list = service.selectHealList(map);
		model.addAttribute("keyfield", keyfield);
		model.addAttribute("keyword", keyword);
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		
		return"healthy_M";
	}

	@GetMapping("/health/healWrite")
	public String getHealthWriteForm(HttpServletRequest request,HttpSession session,Model model) {
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			session.setAttribute("preURL", request.getContextPath()+"/heath/healWrite");
		}else {
			if(user_type.equals("doctor")) {
				return "healthy_writeForm";
			}else {
				MemberVO mem = (MemberVO) user;
				if(mem.getMem_auth() >2) {
					return "healthy_writeForm";
				}else {
					model.addAttribute("message","쓰기 권한이 없습니다.");
					model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
				}
			}
		}
		return "common/resultAlert";
	}
	@PostMapping("/health/healWrite")
	public String getHealthWrite(HealthyBlogVO vo,HttpServletRequest request,HttpSession session,Model model) throws IllegalStateException, IOException {
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			session.setAttribute("preURL", request.getContextPath()+"/heath/healWrite");

		}else {
			if(user_type.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				vo.setMem_num(duser.getMem_num());
				vo.setH_filename(FileUtil.createFile(request, vo.getUpload()));
				service.insertHeal(vo);
				return "redirect:/health/healthBlog";
			}else {
				MemberVO mem = (MemberVO) user;
				if(mem.getMem_auth() >2) {
					vo.setMem_num(mem.getMem_num());
					vo.setH_filename(FileUtil.createFile(request, vo.getUpload()));

					service.insertHeal(vo);
					return "redirect:/health/healthBlog";
				}
				model.addAttribute("message","쓰기 권한이 없습니다.");
				model.addAttribute("url",request.getContextPath()+"/heath/healthBlog");

			}
		}

		return  "common/resultAlert";
	}

	@GetMapping("/health/healthDetail")
	public String getHealthDetail(long healthy_num,HttpSession session,Model model) {

		service.updateHealHit(healthy_num);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("healthy_num", healthy_num);
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");

		if(user != null) {
			if(user_type != null && user.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				model.addAttribute("user_num", duser.getMem_num());
			}else {
				MemberVO muser = (MemberVO) user;
				map.put("user_num", muser.getMem_num());
				model.addAttribute("user_num", muser.getMem_num());
			}

		}else
			map.put("user_num", 0);
		
		HealthyBlogVO vo = service.getHealthy(map);

		model.addAttribute("healthy",vo);
		log.debug("<<<<<<<<<<"+ vo);
		return "healthy_Detail";
	}

	@GetMapping("/health/healthUpdate")
	public String getHealthUpdateForm(long healthy_num,HttpServletRequest request,HttpSession session,Model model) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("healthy_num", healthy_num);
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long mem_num =0;
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");

		}else if( user != null) {
			if(user_type != null) {
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				mem_num= duser.getMem_num();
			}else {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				mem_num= mem.getMem_num();
			}	
			HealthyBlogVO vo = service.getHealthy(map);
			if(mem_num == vo.getMem_num() ) {
				model.addAttribute("healthyBlogVO",vo);
				return "healthy_Update";
			}else {
				model.addAttribute("message","본인 작성 글만 수정 가능합니다.");
				model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
			}
			return  "common/resultAlert";
		}
		return  "common/resultAlert";
	}

	@PostMapping("/health/healthUpdate")
	public String getHealUpdate(HealthyBlogVO vo,HttpSession session,HttpServletRequest request,Model model) throws IllegalStateException, IOException {
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long mem_num =0;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("healthy_num", vo.getHealthy_num());
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else if(user != null) {
			if(user_type != null) {
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				mem_num= duser.getMem_num();
			}else {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				mem_num= mem.getMem_num();
			}	
			map.put("user_num", mem_num);
			HealthyBlogVO db_vo = service.getHealthy(map);
			if( mem_num == db_vo.getMem_num() ) {
				if(vo.getUpload() != null) {
					vo.setH_filename(FileUtil.createFile(request, vo.getUpload()));
					FileUtil.removeFile(request, db_vo.getH_filename());
				}
				service.updateHeal(vo);

				return "redirect:/health/healthDetail?healthy_num="+vo.getHealthy_num();
			}else {
				model.addAttribute("message","본인 작성 글만 수정 가능합니다.");
				model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
			}
		}


		return  "common/resultAlert";
	}
	@GetMapping("/health/healthDelete")
	public String getHealthDelete(long healthy_num,HttpServletRequest request,HttpSession session,Model model) {
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long mem_num =0;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("healthy_num", healthy_num);

		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else if(user != null) { 

			if(user_type != null) {
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				mem_num= duser.getMem_num();
			}else {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				mem_num= mem.getMem_num();
			}	
			map.put("user_num", mem_num);
			HealthyBlogVO vo = service.getHealthy(map);
			if(mem_num== vo.getMem_num() ) {
				service.deleteHeal(healthy_num);
				model.addAttribute("message","게시글이 삭제 되었습니다.");
				model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
			}else {
				model.addAttribute("message","본인 작성 글만 삭제 가능합니다.");
				model.addAttribute("url",request.getContextPath()+"/health/healthBlog");
			}
		}
		return  "common/resultAlert";
	}


}

