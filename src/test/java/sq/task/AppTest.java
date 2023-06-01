package sq.task;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import sq.task.config.TaskExcuteHanderConfig;
import sq.task.entity.TaskConfig;
import sq.task.entity.TaskLog;
import sq.task.entity.enums.TaskStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        /**
         * 创建任务
         */
        Task<String> stringTask = new Task<>("1task");
        TaskConfig taskConfig = new TaskConfig();
        taskConfig.setSecond(10);
//        stringTask.setTaskConfig(taskConfig);
        stringTask.setAction(()->{
            stringTask.setCommunicationInformation("实时通讯信息"+new Date());
            List<TaskLog> taskLogs = stringTask.getTaskLogs();
            taskLogs.add(new TaskLog("111111111->正在执行的任务",new Date()));
            return "任务1结果";
        });
        //任务2
        Task<String> stringTask2 = new Task<>("2task");
        stringTask2.setAction(()->{
            stringTask2.setCommunicationInformation("22222222-》实时通讯信息"+new Date());
            List<TaskLog> taskLogs = stringTask2.getTaskLogs();
            taskLogs.add(new TaskLog("22222222-》正在执行的任务",new Date()));
            return "任务2结果";
        });
        //task3
        Task<String> stringTask3 = new Task<>("3task");
        stringTask3.setAction(()->{
            String previous = stringTask3.getPrevious();
            System.out.println("==========task3上一步执行结果"+previous+"=====================");
            stringTask3.setCommunicationInformation("3333333-》实时通讯信息"+new Date());
            List<TaskLog> taskLogs = stringTask3.getTaskLogs();
            taskLogs.add(new TaskLog("333333-》正在执行的任务",new Date()));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            taskLogs.add(new TaskLog("3333-》正在执行的任务",new Date()));
            return "任务3结果";
        });
        //在任务2执行完成后执行任务3
        stringTask2.setNextTask(stringTask3);
        //加入任务队列
        ArrayList<Task<String>> tasks = new ArrayList<>();
        tasks.add(stringTask);
        tasks.add(stringTask2);
        //执行任务
        TaskExcuteHanderConfig taskExcuteHanderConfig = new TaskExcuteHanderConfig(5,10,100);

        TaskExcuteHander<String> stringTaskExcuteHander = new TaskExcuteHander<>(taskExcuteHanderConfig);
        //启动任务
        stringTaskExcuteHander.start(tasks);
        //在执行中添加任务
        Task<String> stringTask4 = new Task<>("4task");
        stringTask4.setAction(()->{
            stringTask.setCommunicationInformation("实时通讯信息"+new Date());
            List<TaskLog> taskLogs = stringTask.getTaskLogs();
            taskLogs.add(new TaskLog("44444444444->正在执行的任务",new Date()));
            return "任务4结果";
        });
        //添加并立即执行（也会遵循taskConfig配置）
        stringTaskExcuteHander.addTaskAndExecute(stringTask4);
        for (int i = 0; i < 10; i++) {
            Task<String> stringTask5 = new Task<>("task----"+i);
//            stringTask5.setTaskConfig(taskConfig);
            stringTask5.setAction(()->{
                Thread.sleep(30000);
                stringTask.setCommunicationInformation("实时通讯信息"+new Date());
                List<TaskLog> taskLogs = stringTask.getTaskLogs();
                taskLogs.add(new TaskLog("44444444444->正在执行的任务",new Date()));
                return "任务4结果";
            });
            //添加并立即执行（也会遵循taskConfig配置）
            stringTaskExcuteHander.addTaskAndExecute(stringTask5);
        }


        boolean sss = true;
        //循环查看任务结果
        while (sss){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (tasks.size()==0){
                break;
            }
            System.out.println("====================================");
            System.out.println("总任务个数"+stringTaskExcuteHander.getTaskSize());
            System.out.println("已完成任务个数"+stringTaskExcuteHander.getTaskOverSize());
            System.out.println("正在运行任务个数"+stringTaskExcuteHander.getRunningTasKSize());
            System.out.println("====================================");
//            for (Task<String> task : tasks) {
//                TaskStatus status = task.getStatus();
//                System.out.println(task.getTaskNmae()+":当前任务状态："+status.getValue());
//                System.out.println(task.getTaskNmae()+"任务实时信息："+task.getCommunicationInformation());
//                List<TaskLog> taskLogs = task.getTaskLogs();
//                for (TaskLog taskLog : taskLogs) {
//                    System.out.println(task.getTaskNmae()+"任务日志："+taskLog.getLog());
//                }
//                if (status.equals(TaskStatus.SUCCESS)||status.equals(TaskStatus.FAIL)) {
//                    System.out.println("============执行完毕===================");
//                    System.out.println("============执行结果"+task.getTaskNmae()+"::"+task.getResult()+"===================");
//                }
//            }

            if (stringTaskExcuteHander.getTaskSize().intValue()==stringTaskExcuteHander.getTaskOverSize().intValue()){
                sss=false;
            }
        }
    }
}
