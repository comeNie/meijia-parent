package com.simi.action.partners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.StringUtil;
import com.meijia.utils.common.extension.StringHelper;
import com.simi.action.admin.AdminController;
import com.simi.models.AuthorityEditModel;
import com.simi.models.TreeModel;
import com.simi.models.extention.TreeModelExtension;
import com.simi.oa.auth.AuthPassport;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.admin.AdminAuthorityService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServiceTypeTreeVo;

@Controller
@RequestMapping(value = "/partnerServiceType")
public class PartnerServiceTypeController extends AdminController {

	@Autowired
	private AdminAuthorityService adminAuthorityService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	/**
	 * 树形展示权限列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/chain", method = { RequestMethod.GET })
	public String chain(HttpServletRequest request, Model model) {
		if (!model.containsAttribute("contentModel")) {
			String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
			List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain(new ArrayList<Long>()), null, null,
					StringHelper.toIntegerList(expanded, ","));
			List<TreeModel> treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, false, false, children)));
			String jsonString = JSONArray.fromObject(treeModels, new JsonConfig()).toString();
			model.addAttribute("contentModel", jsonString);
		}
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		return "partners/partnerServiceTypeList";
	}

	/**
	 * 根据id添加同级或者子级节点
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return 编辑页面
	 */
	@AuthPassport
	@RequestMapping(value = "/add/{id}", method = { RequestMethod.GET })
	public String add(HttpServletRequest request, Model model, @PathVariable(value = "id") Integer id) {
		if (!model.containsAttribute("contentModel")) {
			AuthorityEditModel authorityEditModel = new AuthorityEditModel();
			authorityEditModel.setParentId(id);
			model.addAttribute("contentModel", authorityEditModel);
		}
		List<TreeModel> treeModels;
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		if (id != null && id > 0) {
			List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain(new ArrayList<Long>()), id, null,
					StringHelper.toIntegerList(expanded, ","));
			treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, false, false, children)));
		} else {
			List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain(new ArrayList<Long>()), null, null,
					StringHelper.toIntegerList(expanded, ","));
			treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, true, false, children)));
		}
		model.addAttribute(treeDataSourceName, JSONArray.fromObject(treeModels, new JsonConfig()).toString());
		return "partners/partnerServiceTypeForm";
	}

	/**
	 * 根据页面选择的id,增加新节点
	 * 
	 * @param request
	 * @param model
	 * @param adminAuthorityVo
	 * @param id
	 * @param result
	 * @return 权限的树形展示页面
	 */
	@AuthPassport
	@RequestMapping(value = "/add/{id}", method = { RequestMethod.POST })
	public String add(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") PartnerServiceTypeTreeVo partnerServiceTypeVo,
			@PathVariable(value = "id") String id, BindingResult result) {
		if (result.hasErrors())
			return add(request, model, Integer.valueOf(id));
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);

		PartnerServiceType partnerServiceType = partnerServiceTypeService.initPartnerServiceType();
		partnerServiceType.setName(partnerServiceTypeVo.getName());
		partnerServiceType.setParentId(partnerServiceTypeVo.getParentId());
		/*
		 * String levelCode = ""; int count =
		 * adminAuthorityService.selectMaxId()+1; if
		 * (partnerServiceTypeVo.getParentId() != null &&
		 * partnerServiceTypeVo.getParentId() > 0) {
		 * partnerServiceType.setParentId(partnerServiceType.getParentId());
		 * String parentLevelCode =
		 * adminAuthorityService.selectByPrimaryKey(partnerServiceTypeVo
		 * .getParentId()).getLevelCode(); levelCode = count+ "," +
		 * parentLevelCode;
		 * 
		 * }else{ levelCode = count + levelCode ; }
		 */
		partnerServiceTypeService.insertSelective(partnerServiceType);
		if (returnUrl == null)
			returnUrl = "partners/partnerServiceTypeList";
		return "redirect:" + returnUrl;
	}

	/**
	 * 根据id编辑对应的权限
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return 跳转到编辑页面
	 */
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = { RequestMethod.GET })
	public String edit(HttpServletRequest request, Model model, @PathVariable(value = "id") Long id) {
		if (!model.containsAttribute("contentModel")) {
			PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(id);
			model.addAttribute("contentModel", partnerServiceType);
		}
		List<TreeModel> treeModels;
		PartnerServiceType editModel = (PartnerServiceType) model.asMap().get("contentModel");
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		if (editModel.getParentId() != null && editModel.getParentId() > 0) {
			List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain(new ArrayList<Long>()), editModel
					.getParentId().intValue(), null, StringHelper.toIntegerList(expanded, ","));
			treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, false, false, children)));
		} else {
			List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain(new ArrayList<Long>()), null, null,
					StringHelper.toIntegerList(expanded, ","));
			treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, true, false, children)));
		}
		model.addAttribute("treeDataSource", JSONArray.fromObject(treeModels, new JsonConfig()).toString());

		return "partners/partnerServiceTypeForm";
	}

	/**
	 * 根据id更新权限
	 * 
	 * @param request
	 * @param model
	 * @param adminAuthority
	 * @param id
	 * @param result
	 * @return 跳转到权限的树形列表
	 */
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = { RequestMethod.POST })
	public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") PartnerServiceType partnerServiceType,
			@PathVariable(value = "id") Long id, BindingResult result) {
		if (result.hasErrors())
			return edit(request, model, id);
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		
		if (partnerServiceType != null) {
			partnerServiceType.setId(Long.valueOf(id));
			
			String parentIdStr = request.getParameter("parentId");
			if (!StringUtil.isEmpty(parentIdStr)) {
				Long parentId = Long.valueOf(parentIdStr);
				partnerServiceType.setParentId(parentId);
			}
			
			partnerServiceTypeService.updateByPrimaryKeySelective(partnerServiceType);
		}
		if (returnUrl == null)
			returnUrl = "partnerServiceType/chain";
		return "redirect:" + returnUrl;
	}

	/**
	 * 根据id删除权限
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return 跳转到权限树形展示
	 */
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET })
	public String delete(HttpServletRequest request, Model model, @PathVariable(value = "id") String id) {
		Long ids = Long.valueOf(id.trim());
		// 根据id查找出对应的该权限对象
		// int result =
		// adminAuthorityService.deleteAuthorityByRecurision(adminAuthority);
		partnerServiceTypeService.deleteByPrimaryKey(ids);
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if (returnUrl == null)
			returnUrl = "partnerServiceType/chain";
		return "redirect:" + returnUrl;
	}

}
