class Solution {
    /* Leetcode 324. Wiggle Sort II
    Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
    调整的方法是找到数组的中间的数，相当于把有序数组从中间分成两部分，然后从前半段的末尾取一个，在从后半的末尾取一个，
    这样保证了第一个数小于第二个数，然后从前半段取倒数第二个，从后半段取倒数第二个，
    这保证了第二个数大于第三个数，且第三个数小于第四个数，以此类推直至都取完。
    */
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        int end = nums.length-1;
        int mid = end/2;
        Boolean flag = true;
        int[] copy = new int[nums.length];
        int idx = 0;
        
        while (idx<nums.length) {
            if (flag) {
                copy[idx] = nums[mid];
                flag = false;
                mid--;
            } else {
                copy[idx] = nums[end];
                flag=true;
                end--;
            }
            idx++;
        }
        for(int i = 0 ; i < nums.length;i++){
            nums[i] = copy[i];
        }
    }

    /*218. The Skyline Problem
    用priority queue做
    https://www.youtube.com/watch?v=GSBLe8cKu0s
    懒得写java了，下面是python code
    def getSkyline(self, buildings: List[List[int]]) -> List[List[int]]:
        points = []
        for lst in buildings:
            start = [lst[0], lst[2], 'begin']
            end = [lst[1], lst[2], 'end']
            points.append(start)
            points.append(end)
        points = sorted(points, key=lambda x: (x[0], x[1], x[2]))
        
        pq = [0]
        result = []
        maxHeight = -1
        
        for lst in points:
            if lst[2]=='begin':
                pq.append(lst[1]) 
                pq.sort()
                if (lst[1]>maxHeight):
                    maxHeight = max(maxHeight, lst[1])
                    if (len(result)!=0 and lst[0] == result[-1][0]):
                        result[-1][1] = maxHeight
                    else:
                        result.append([lst[0], maxHeight])
            else:
                pq.remove(lst[1])
                maxHeight = pq[-1]
                if (lst[1] > maxHeight or maxHeight==0):
                    maxHeight = pq[-1]
                    result.append([lst[0], maxHeight])
        return result
    */

}