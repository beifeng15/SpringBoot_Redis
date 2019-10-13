list相关操作函数：https://www.iteye.com/blog/357029540-2388987
hash相关操作函数：https://www.iteye.com/blog/357029540-2389045 

任务队列的一种简单使用：

  //1.付款完成后，会根据用户的收货地址和商家的发货地址 生成一个队列（任务）
    public void listQueueInit(String cardId){
        String key ="prod:"+cardId;// 初始化的KEY 待有多少任务要完成

        list.leftPushAll(key,"1商家发货","2小哥发货","3北京海定区某小区--》首都机场","4北京机场--》南京机场","5机场-->建邺区","建邺区--》");

    }

    //2.触发事件
    public void listQueueTouch(String cardId){
        String key ="prod:+"cardId+":succ";//已完成任务队列
        //rightPopAndLeftPush:移除集合中右边的元素，同时在左边加入一个元素。
        //下面的两个参数都是key
        list.rightPopAndLeftPush("prod:"+cardId,key);
    }
    //3查询:客户查询：我的快递到哪了？
    public List<String > listQueueSucc(String cardId){
        String key ="prod:"+cardId+":succ";//已完成任务队列
        return list.range(key,0,-1);
    }
    //4.物流查询：当前快递还有多少任务没有执行
    public List<String> listQueueWait(String cardId)J{
        String key ="prod:"+cardId;//初始化的KEY 待有多少任务要完成
        return list.range(key,0,-1);
    }
