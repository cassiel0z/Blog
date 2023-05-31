<template>
    <v-carousel :show-arrows="false">
        <v-carousel-item v-for="(item, i) in items" :key="i" :src="item"></v-carousel-item>
    </v-carousel>
    <table class="news">
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/29102.htm" target="_blank">我校研究生在国际期刊《Journal of Materials
                    Chemistry C》上发表激光照明相关研究论文</a></td>
            <td>2022-11-10</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/29092.htm" target="_blank">我校第三十六届运动会田径比赛开幕</a></td>
            <td>2022-11-10</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/29052.htm" target="_blank">我校教师在能源学术期刊《Energy Storage
                    Materials》上发表综述文章</a></td>
            <td>2022-11-08</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/29042.htm"
                    target="_blank">中国计量大学党委理论学习中心组举行“学习贯彻党的二十大精神”专题学习会</a></td>
            <td>2022-11-08</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/29032.htm" target="_blank">我校青年教师在高水平国际学术期刊《Chemical Engineering
                    Journal》上发表最新研究成果</a></td>
            <td>2022-11-07</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/28982.htm" target="_blank">我校教师荣获“青春飞扬·2023中国青年版画家提名”</a></td>
            <td>2022-11-06</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/28862.htm" target="_blank">我校研究生在国际期刊《Physics of
                    Fluids》上发表研究论文</a></td>
            <td>2022-11-01</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/28842.htm" target="_blank">我校结对帮扶事迹入选教育部乡村振兴优秀案例</a></td>
            <td>2022-11-01</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/28752.htm" target="_blank">我校学生荣获2022年浙江省普通本科国家奖学金省级特别评选推荐资格</a>
            </td>
            <td>2022-10-27</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1100/28712.htm" target="_blank">我校召开领导干部会议传达学习贯彻党的二十大精神</a></td>
            <td>2022-10-25</td>
        </tr>
    </table>
    <table class="medias">
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/29082.htm" target="_blank">[媒体传真]中量大用“行走的思政课”宣讲党的二十大精神</a></td>
            <td>2022-11-10</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/29072.htm" target="_blank">[媒体传真]上周末的这项考试 杭州考点参考人数全国第一</a></td>
            <td>2022-11-08</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/29022.htm" target="_blank">[中新网浙江]浙江两所高校联合开展主题党日活动</a></td>
            <td>2022-11-07</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/28952.htm" target="_blank">[教育参阅]中国计量大学“8090”博士理论宣讲团工作报告</a></td>
            <td>2022-11-04</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/29012.htm" target="_blank">[媒体传真]学习贯彻党的二十大精神扎实做好新时代高校统战工作</a>
            </td>
            <td>2022-11-03</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/28892.htm" target="_blank">[中国教育报]推进“老本行”学科建设 打造特色一流大学</a></td>
            <td>2022-11-02</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/28902.htm" target="_blank">[新浪浙江]乡村未来 艺术无界 广德市首届艺术乡建论坛成功召开</a>
            </td>
            <td>2022-11-02</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/28882.htm" target="_blank">[钱塘发布]“追随足迹”现场学
                    在新时代十年的生动实践中学习宣传贯彻二十大精神</a></td>
            <td>2022-11-01</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/28832.htm"
                    target="_blank">[中国教育在线]中国计量大学浙中科创走廊成果转化对接会暨现代科技学院科技成果推介会成功召开</a></td>
            <td>2022-10-31</td>
        </tr>
        <tr>
            <td><a href="https://www.cjlu.edu.cn/info/1101/28822.htm" target="_blank">[媒体传真]她获得国家奖学金特别评审奖，来看山城“菇”娘的新农人之路</a>
            </td>
            <td>2022-10-29</td>
        </tr>

    </table>
</template>

<script lang="ts" setup>
import '@/styles/frontPage.css'
import img1 from '@/assets/img1.jpg'
import img2 from '@/assets/img2.jpg'
import img3 from '@/assets/img3.jpg'
import img4 from '@/assets/img4.jpg'
import img5 from '@/assets/img5.jpg'

let items = [img1, img2, img3, img4, img5]

</script>
