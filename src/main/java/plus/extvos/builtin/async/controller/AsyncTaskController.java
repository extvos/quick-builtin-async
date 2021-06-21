package plus.extvos.builtin.async.controller;

import plus.extvos.builtin.async.dto.AsyncTask;
import plus.extvos.builtin.async.service.AsyncTaskContainer;
import plus.extvos.builtin.async.service.AsyncTaskRunner;
import plus.extvos.restlet.Result;
import plus.extvos.restlet.exception.RestletException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mingcai SHEN
 */
@RestController
@Api(tags = {"异步任务"})
@RequestMapping("/_builtin/async/task/")
public class AsyncTaskController {
    @Autowired
    private AsyncTaskContainer asyncTaskContainer;
    @Autowired
    private AsyncTaskRunner asyncTaskRunner;

    @ApiOperation(value = "异步任务详情")
    @GetMapping("/{id}")
    public Result<AsyncTask> getTaskById(@PathVariable("id") String id) throws RestletException {
        AsyncTask t = asyncTaskContainer.get(id);
        return Result.data(t).success();
    }

    @ApiOperation(value = "中止异步任务")
    @PostMapping("/{id}/abort")
    public Result<AsyncTask> abortTaskById(@PathVariable("id") String id) throws RestletException {
        AsyncTask t = asyncTaskContainer.get(id);
        if (asyncTaskRunner.abort(t)) {
            t.setStatus(-1);
            t.setError("Aborted by user");
        }
        return Result.data(t).success();
    }
}
