package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/message")
class MessageController {

    @PostMapping("/getMessage")
    @ResponseBody
    public Map<String, Object> getMessage(@RequestParam("senderId") int senderId,
    @RequestParam("receiverId") int receiverId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("senderId",senderId);
        result.put("receiverId",receiverId);

        return result;
    }

}