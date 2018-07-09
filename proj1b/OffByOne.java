/**
 * Created by 11519 on 2018/7/9.
 * 自定义字符比较规则: 相差1就是相等
 */
public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}
