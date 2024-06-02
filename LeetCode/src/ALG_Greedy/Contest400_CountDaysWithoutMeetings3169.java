/**
 * You are given a positive integer days representing the total number of days an employee is available for work (starting from day 1). You are also given a 2D array meetings of size n where, meetings[i] = [start_i, end_i] represents the starting and ending days of meeting i (inclusive).
 * Return the count of days when the employee is available for work but no meetings are scheduled.
 * Note: The meetings may overlap.
 * Example 1:
 * Input: days = 10, meetings = [[5,7],[1,3],[9,10]]
 * Output: 2
 * Explanation:
 * There is no meeting scheduled on the 4th and 8th days.
 * Example 2:
 * Input: days = 5, meetings = [[2,4],[1,3]]
 * Output: 1
 * Explanation:
 * There is no meeting scheduled on the 5th day.
 * Example 3:
 * Input: days = 6, meetings = [[1,6]]
 * Output: 0
 * Explanation:
 * Meetings are scheduled for all working days.
 * Example 4:
 * days =8, meetings =[[3,4],[4,8],[2,5],[3,8]]
 * Output: 1
 * Constraints:
 * 1 <= days <= 109
 * 1 <= meetings.length <= 105
 * meetings[i].length == 2
 * 1 <= meetings[i][0] <= meetings[i][1] <= days
 */
package ALG_Greedy;
import java.util.Arrays;

public class Contest400_CountDaysWithoutMeetings3169 {
    public static void main(String[] args) {
        int days = 8;
        int[][] meetings = {{3,4},{4,8},{2,5},{3,8}};
        System.out.println(countDays(days,meetings));
    }
    /**Greedy
     * O(nlogn + n)
     * O(n)
     * 思路:
     * 通过局部最优解找到全局最优解(找到每天排除重叠子问题的天数->找到所有天数)
     * 这道题的关键在于找出重叠的天数
     * 1.先排序，按照start的天开始排序，方便计算重叠天数
     * 2.从第1天开始，遍历meetings，按照不同的情况计算curdays
     *   分成两种1.有重叠天数 2.没有重叠天数
     *   重叠分成两种情况
     *      1.完全重叠  [2,3,4,5] VS [3,4]
     *           完全重叠时要特别处理的一点 meetings[i][1] = meetings[i-1][1];
     *           这是为了记录出现过的最远的天数，为了保证后面的问题都能被正确判定为重叠天数
     *      2.不完全重叠 比如  [2,3,4,5] VS [3,4,5,6,7,8]
     */
    public static int countDays(int days, int[][] meetings) {
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0],b[0])); //O(nlogn)
        int res = days - (meetings[0][1]-meetings[0][0]+1);
        //System.out.println(Arrays.deepToString(meetings));
        for(int i=1; i<meetings.length; i++){
            int curdays = (meetings[i][1]-meetings[i][0]+1);
            if(meetings[i][0] <= meetings[i-1][1]){
                if(meetings[i][1] > meetings[i-1][1]){
                    curdays -= (meetings[i-1][1]-meetings[i][0]+1);
                }
                else{
                    curdays -= (meetings[i][1]-meetings[i][0]+1);
                    meetings[i][1] = meetings[i-1][1];//Key
                }
            }
            res -= curdays;
        }
        return res;
    }
}
