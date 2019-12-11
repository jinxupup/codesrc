import com.jjb.cmp.biz.dao.TmCmpContentDtoDao;

import com.jjb.cmp.biz.dao.impl.TmCmpContentDtoDaoImpl;
import com.jjb.cmp.dto.TmCmpContentDto;
import com.jjb.cmp.infrastructure.TmCmpContent;
import com.jjb.unicorn.facility.exception.ProcessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.io.FileUtils.getFile;

/**
 * @ClassName Test
 * Company jydata-tech
 * @Description TODO
 * Author zhangwenlu
 * Date 2019/4/26 14:56
 * Version 1.0
 */
public class Test {

    /**
     * wxl
     * 删除fastdfs地址对应的一行记录
     */
    public int deleteByKey(TmCmpContent tmCmpContent) {
        if (tmCmpContent.getContAbsPath() != null) {
            delete(String.valueOf(tmCmpContent.getId()), tmCmpContent);
        } else {
            throw new ProcessException("ContAbsPath 为空 path 地址为空！！！");
        }
        return 0;
    }

    private void delete(String s, TmCmpContent tmCmpContent) {
    }


    @org.junit.Test
    public void lala() {
        TmCmpContentDtoDaoImpl tmCmpContentDtoDao = new TmCmpContentDtoDaoImpl();
        TmCmpContentDto tmCmpContentDto = new TmCmpContentDto();

        List<TmCmpContentDto> list = tmCmpContentDtoDao.deleteFile1(tmCmpContentDto);
        System.out.println(list);


    }


    @org.junit.Test
    public void heihei() {
        HashMap<String, String> map = new HashMap<>();
        map.put("111", "11111111");
        map.put("222", "22222222");
        map.put("333", "33333333");
        map.put("444", "44444444");
        Set<String> strings = map.keySet();
        System.out.println(strings);
        for (String string : strings) {

            System.out.print(map.get(string));
        }

    }

    @org.junit.Test
    public void xixixix() {
        //{lala=啦啦}{haha=哈哈, lala=啦啦}

        String str = "{haha=哈哈,lala=啦啦}";
        String replace = str.replace("{", "").replace("}", "");
        System.out.println(replace);
        String[] one = replace.split(",");
        for (String s : one) {
            String[] two = s.split("=");
            System.out.println(two[0]);
            System.out.println(two[1]);
        }

//        Map<String, String> params = new HashMap<>();
//        params.put("imageMap", "{imageMap={111=11111111}}");
//        System.out.println(params);
//        System.out.println(params.get("imageMap"));
    }

    @org.junit.Test
    public void xixixixi() {
        File file = new File("");
        File fileName = getFile();

        System.out.println(fileName);
    }

}
