# quick-builtin-async

模块提供一个统一的异步接口处理支持

## 关于模块

模块基于`Spring`的异步方式做了一个封装，异步接口在调用的时候会先返回一个标准接口数据，给出当前任务的`id`和标题等，终端可以根据该`id`定时去查询任务的执行状态以及执行的百分比。



## 相关配置项

```ini
quick.builtin.async.core-pool-size=5
quick.builtin.async.max-pool-size=10
quick.builtin.async.queue-capacity=100
quick.builtin.async.keep-alive-time=100
quick.builtin.async.thread-name=
```

## 用法举例

```java
@GetMapping("/example/async")
public Result<AsyncTask> exampleAsync(@RequestParam("duration") int duration) throws RestletException {
    AsyncTask t = asyncTaskRunner.make((ai) -> {
        try {
            for (int i = 1; i <= duration; i++) {
                log.info(Thread.currentThread().getName() + "----------异步：>" + i);
                Thread.sleep(200);
                double d = (double) i / (double) duration;
                ai.percentage((int) (d * 100));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Thread.currentThread().getName() + "执行异常:" + ex.getMessage();
        }
        return Thread.currentThread().getName() + "执行完毕";
    }, "example-async-" + duration);
    asyncTaskRunner.start(t);
    return Result.data(t).success();
}
```

- 调用`asyncTaskRunner.make`生成一个新的`AsyncTask`, 
  - `AsyncRunnable`里面的`Object run(AsyncIndicator ai) throws RuntimeException`是要做异步执行的执行代码，
  - 其中`AsyncIndicator ai`是用于返回执行百分比的一个接口，
- 调用`asyncTaskRunner.start`执行刚才创建的`AsyncTask`实例；
- 接口返回输出刚才创建的`AsyncTask`实例；
- 终端根据获得的`AsyncTask`实例查询任务状态；



## `AsyncTask`数据结构

```json
{
    "error": "", // 任务执行的异常错误信息
    "identity": "", // 任务ID
    "percentage": 0, // 任务执行的百分比 0 ~ 100
    "result": {}, // 任务执行成功后的返回数据，结构跟任务相关
    "status": 0, // 任务的状态： -1: 失败, 0: 创建; 1: 运行中; 2: 已完成
    "subject": "" // 任务的主题
}
```



## 相关接口

### 异步任务详情

终端通过该接口获取异步任务的执行情况。

**接口地址**:`/_builtin/async/task/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | -------- | -------- | -------- | ------ |
| id       | id       | path     | true     | string   |        |


**响应状态**:


| 状态码 | 说明         | schema            |
| ------ | ------------ | ----------------- |
| 200    | OK           | Result«AsyncTask» |
| 401    | Unauthorized |                   |
| 403    | Forbidden    |                   |
| 404    | Not Found    |                   |


**响应参数**:


| 参数名称               | 参数说明 | 类型           | schema         |
| ---------------------- | -------- | -------------- | -------------- |
| code                   |          | integer(int32) | integer(int32) |
| count                  |          | integer(int64) | integer(int64) |
| data                   |          | AsyncTask      | AsyncTask      |
| &emsp;&emsp;error      |          | string         |                |
| &emsp;&emsp;identity   |          | string         |                |
| &emsp;&emsp;percentage |          | integer(int32) |                |
| &emsp;&emsp;result     |          | object         |                |
| &emsp;&emsp;status     |          | integer(int32) |                |
| &emsp;&emsp;subject    |          | string         |                |
| error                  |          | string         |                |
| msg                    |          | string         |                |
| page                   |          | integer(int64) | integer(int64) |
| pageSize               |          | integer(int64) | integer(int64) |
| total                  |          | integer(int64) | integer(int64) |


**响应示例**:
```javascript
{
	"code": 0,
	"count": 0,
	"data": {
		"error": "",
		"identity": "",
		"percentage": 0,
		"result": {},
		"status": 0,
		"subject": ""
	},
	"error": "",
	"msg": "",
	"page": 0,
	"pageSize": 0,
	"total": 0
}
```



### 中止异步任务

终端可以通过该接口主动终止异步任务的执行。

**接口地址**:`/_builtin/async/task/{id}/abort`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | -------- | -------- | -------- | ------ |
| id       | id       | path     | true     | string   |        |


**响应状态**:


| 状态码 | 说明         | schema            |
| ------ | ------------ | ----------------- |
| 200    | OK           | Result«AsyncTask» |
| 201    | Created      |                   |
| 401    | Unauthorized |                   |
| 403    | Forbidden    |                   |
| 404    | Not Found    |                   |


**响应参数**:


| 参数名称               | 参数说明 | 类型           | schema         |
| ---------------------- | -------- | -------------- | -------------- |
| code                   |          | integer(int32) | integer(int32) |
| count                  |          | integer(int64) | integer(int64) |
| data                   |          | AsyncTask      | AsyncTask      |
| &emsp;&emsp;error      |          | string         |                |
| &emsp;&emsp;identity   |          | string         |                |
| &emsp;&emsp;percentage |          | integer(int32) |                |
| &emsp;&emsp;result     |          | object         |                |
| &emsp;&emsp;status     |          | integer(int32) |                |
| &emsp;&emsp;subject    |          | string         |                |
| error                  |          | string         |                |
| msg                    |          | string         |                |
| page                   |          | integer(int64) | integer(int64) |
| pageSize               |          | integer(int64) | integer(int64) |
| total                  |          | integer(int64) | integer(int64) |


**响应示例**:
```javascript
{
	"code": 0,
	"count": 0,
	"data": {
		"error": "",
		"identity": "",
		"percentage": 0,
		"result": {},
		"status": 0,
		"subject": ""
	},
	"error": "",
	"msg": "",
	"page": 0,
	"pageSize": 0,
	"total": 0
}
```
