package io.gaecfov.pulsar.console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangqin
 */
@Controller
public class ViewController {

  @RequestMapping("/")
  public String index() {
    return "index.html";
  }
}