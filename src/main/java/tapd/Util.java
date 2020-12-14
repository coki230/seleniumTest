package tapd;

public class Util {
    public static String getTapdDept(String dept) {
        switch(dept){
            case "智能运维产品应用开发组" :
                return "智能运维产品部";
            case "智能运维产品部业务组" :
                return "智能运维产品部";
            case "智能运维产品部业务开发组" :
                return "智能运维产品部";
            case "智能运维产品部大数据分析组" :
                return "智能运维产品部";
            case "研发一部" :
                return "行业应用-研发一部";
            case "研发二部" :
                return "行业应用-研发二部";
            case "研发三部" :
                return "行业应用-研发三部";
            case "研发四部" :
                return "行业应用-研发四部";
            case "研发五部" :
                return "行业应用-研发五部";
            case "研发六部" :
                return "行业应用-研发六部";
            case "研发七部" :
                return "行业应用-研发七部";
            case "研发八部" :
                return "行业应用-研发八部";
            case "新媒体综合产品开发部业务逻辑组" :
                return "新媒体基础平台";
            case "新媒体基础平台物联接入开发组" :
                return "新媒体基础平台";
            case "新媒体基础平台开发部综合开发组" :
                return "新媒体基础平台";
            case "新媒体基础平台开发部流媒体组" :
                return "新媒体基础平台";
            case "新媒体基础平台开发部测试组" :
                return "新媒体基础平台";
            case "新媒体基础平台标准互联开发组" :
                return "新媒体基础平台";
            case "行业应用开发技术支持部" :
                return "行业应用开发技术支持部";
            case "基础产品开发部测试组" :
                return "新媒体基础平台";
            case "云计算开发部平台开发组" :
                return "云计算平台";
            default :
                return "其他";
        }
    }
}
