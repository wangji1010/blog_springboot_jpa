package com.wj.blogs.web.admin;

import com.wj.blogs.po.Type;
import com.wj.blogs.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
   private TypeService typeService;

    @GetMapping("/types")
    //设置默认条数排序方式
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        //将查询到的数据放在模型中
        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/type_input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "admin/type_input";
        }
        Type type1 = typeService.saveType(type);
        if (type1==null){
            //失败
            attributes.addFlashAttribute("message","操作失败！");
        }else {
            //成功
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/type_input";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable("id") Long id ,RedirectAttributes attributes){
        if (result.hasErrors()){
            return "admin/type_input";
        }
        Type type1 = typeService.updateType(id,type);
        if (type1==null){
            //失败
            attributes.addFlashAttribute("message","更新失败！");
        }else {
            //成功
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/types";
    }

    @GetMapping("/test")
    //设置默认条数排序方式
    @ResponseBody
    public Page<Type> typs(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        //将查询到的数据放在模型中
        Page<Type> types = typeService.listType(pageable);

        return types;
    }



}
