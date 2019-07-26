package com.tsa.supplier.web.admin;

import com.tsa.supplier.service.jms.IJMSListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/admin/jms/listener")
public class JMSListenerController {

    private IJMSListenerService sourcingListenerService;

    @Autowired
    public JMSListenerController(IJMSListenerService sourcingListenerService) {
        this.sourcingListenerService = sourcingListenerService;
    }

    @GetMapping
    public ResponseEntity<?> isRunning() {
        return ResponseEntity.ok(this.sourcingListenerService.isRunning());
    }

    // TODO security should be enabled
    @PostMapping("/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void start() {
        this.sourcingListenerService.start();
    }

    // TODO security should be enabled
    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stop() {
        this.sourcingListenerService.stop();
    }

    // TODO security should be enabled
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refresh() {
        this.sourcingListenerService.refresh();
    }

}