package iptv.web;

import iptv.service.PlayListGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/playlist")
public class PlayListController {

    @Autowired
    private PlayListGenerator playListGenerator;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/m3u8")
    public void getPlayList(@PathVariable("name") String name, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"edem_" + name + ".m3u8\"");
        try {
            response.getWriter().print(playListGenerator.generate(name));
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/{name}.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getPlayListJson(@PathVariable("name") String name) {
        return playListGenerator.generate(name);
    }

}
