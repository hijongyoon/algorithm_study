package algorithm;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


class Sort{ // 숫자 정렬.
    int[]array;
    public Sort(int[]array){ this.array=array; int num=1;}
    public void swap(int i,int j){
        int temp=array[i];
        array[i]=array[j];
        array[j]=temp;
    }
    public int getLength(){
        return array.length-1;
    }
    public void selectionSort(){//선택 정렬.
        for(int last=array.length-1;last>0;last--) {
            int max=last;//가장 끝자리 수의 인덱스.
            for(int before=last-1;before>=0;before--){//가장 큰 수의 인덱스를 찾기.
                if(array[max]<array[before])
                    max=before;
            }
            swap(last,max);//가장 끝 수와 가장 큰 수의 자리를 바꿈.
        }
    }
    public void bubbleSort(){//버블 정렬.
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j]>array[j+1])
                    swap(j,j+1);
            }
        }
    }
    public void insertSort(){//삽입정렬.
        for(int i=1;i<array.length;i++){//삽입정렬의 시작은 2번째 배열요소 즉 인덱스가 1인 것 부터 시작한다.
            int temp=array[i];
            int j;
            for(j=i-1;j>=0;j--){
                if(temp>=array[j])//삽입하려는 대상이 만약에 바로 전값보다 크다면 더 이상 비교할 필요 없다.
                    break;
                else if(temp<array[j])//삽입하려는 대상이 전값보다 작다면 계속해서 비교를 해나간다.
                    array[j+1]=array[j];
            }
            array[j+1]=temp;
        }
    }
   public void mergeSort(int start,int end){//합병정렬.
        int middle;
        if(start<end){
            middle=(start+end)/2;
            mergeSort(start,middle);
            mergeSort(middle+1,end);
            merge(start,middle,end);
        }
    }
   public void merge(int start,int middle,int end){
        int i=start,j=middle+1,k=start;
        //T []tmp=new T[array.length]; 자바에선 제네릭을 이용한 인스턴스 생성은 불가능하다. 그래서 난 이 코드를 제네릭에서 int형으로 바꿨다. 나중에 제네릭과 배열에 관한 공부를 해봐야한다.
       int[]tmp=new int[array.length];
       while(i<=middle&&j<=end){
            if(array[i]<=array[j])
                tmp[k++]=array[i++];
            else
                tmp[k++]=array[j++];
        }
        while(i<=middle)
            tmp[k++]=array[i++];
        while(j<=end)
            tmp[k++]=array[j++];
        /*for(int p=start;p<=end;p++)
            array[p]=tmp[p];*/
       if (end + 1 - start >= 0) System.arraycopy(tmp, start, array, start, end + 1 - start);//책보고 연구해보자.
    }
    public void quickSort1(int start,int end){//퀵정렬.
        int pivotIndex;//pivot의 위치.
        if(start<end){
            pivotIndex=partitionLastPivot(start,end);
            quickSort1(start,pivotIndex-1);
            quickSort1(pivotIndex+1,end);
        }
    }
    public void quickSort2(int start,int end){//퀵정렬.
        int pivotIndex;//pivot의 위치.
        if(start<end){
            pivotIndex=partitionMiddlePivot(start,end);
            quickSort2(start,pivotIndex-1);
            quickSort2(pivotIndex+1,end);
        }
    }
    public void quickSort3(int start,int end){//퀵정렬.
        int pivotIndex;//pivot의 위치.
        if(start<end){
            pivotIndex=partitionRandomPivot(start,end);
            quickSort3(start,pivotIndex-1);
            quickSort3(pivotIndex+1,end);
        }
    }
    public int partitionLastPivot(int start,int end){//배열을 pivot 기준으로 분할하고 pivot 의 위치를 반환(pivot 이 마지막 값인 경우).
        int pivot=array[end];
        int i=start-1;
        for(int j=start;j<=end-1;j++){//pivot과 배열요소들을 각각 비교.
            if(array[j]<=pivot){
                i=i+1;
                if(i==j) continue;
                swap(j,i);
            }
        }
        swap(end,i+1);
        return i+1;
    }
    public int partitionMiddlePivot(int start,int end){//첫번째 값, 가운데 값, 마지막 값 중에서 중간값을 pivot 으로 선택.
        int middle=(start+end)/2;
        if(array[start]<=array[middle]&&array[middle]<=array[end]) swap(middle,end);
        else if(array[end]<=array[middle]&&array[middle]<=array[start])swap(middle,end);
        else if(array[middle]<=array[start]&&array[start]<=array[end])swap(start,end);
        else if(array[end]<=array[start]&&array[start]<=array[middle])swap(start,end);
        return partitionLastPivot(start,end);
    }
    public int partitionRandomPivot(int start,int end){//pivot 을 랜덤하게 선택한 후 partitionLastPivot 처럼 작동.
        Random random=new Random();
        int num=random.nextInt(end-start+1)+start;//왜 이렇게 했는지는 조금만 고민해보면 알 수 있다.
        swap(num,end);
        return partitionLastPivot(start,end);
    }
    public void heapSort(){//힙 정렬.
        buildMaxHeap();
        int length=array.length;
        for(int i=array.length-1;i>=1;i--){
            swap(0,i);
            length--;recursiveMaxHeapify(1,length);//이미 제일 윗부분을 바꿧으므로 length--가 먼저 와야한다.
        }
    }
    public void buildMaxHeap(){
        for(int i=array.length/2;i>=1;i--)
            recursiveMaxHeapify(i,array.length);
    }
    public void recursiveMaxHeapify(int i,int length){//recursion 사용. 2*i+1 이 왼쪽 자식, 2*i+2 이 오른쪽 자식.
        i=i-1;
        if(2*i+1>length-1) return;//자식이 없다면 종료. 왜 2*i+1만 했냐면 complete binary heap 같은 경우 왼쪽 자식노드가 없고 오른쪽 자식노드는 있는 부모노드는 없기 때문.
        int k;
        if(2*i+2<=length-1&&array[2*i+1]<array[2*i+2]) k=2*i+2; else k=2*i+1;//자식 중에서 더 값이 큰 노드의 인덱스를 k에 대입.
        if(array[i]>=array[k]) return;//부모가 더 크다면 종료.
        swap(i,k);
        recursiveMaxHeapify(k+1,length);//i-1을 하므로 k+1로 전달.
    }
    public void maxHeapInsert(int key,int heapsize){//heap을 이용한 최대 우선순위 큐-> 새로운 원소를 삽입. 이 메소드를 호출 하기 전에 buildMaxHeap을 먼저 호출.
        heapsize=heapsize+1;
        array[heapsize-1]=key;
        int i=heapsize-1;//삽입될 노드의 인덱스.
        while(i>0&&array[(i-1)/2]<array[i]){//루트 노드가 아니고 삽입될 노드가 부모 노드보다 클 경우.
            swap(i,(i-1)/2);
            i=(i-1)/2;
        }
    }
    public int heapExtractMax(int heapsize){//heap을 이용한 최대 우선순위 큐->가장 큰 값부터 하나씩 뺀다.
        if(heapsize<1)return -1;
        int max=array[0];
        array[0]=array[heapsize-1];
        heapsize--;
        recursiveMaxHeapify(1,heapsize);
        return max;
    }
    public void showArray(){
        for(int e:array)  System.out.print(e+" ");
    }
}

public class SortAlgorithm {
    public static void main(String[] args) {
        int []random1=new int[1000]; int []random2=new int[10000]; int []random3=new int[100000];
        arrayRandomInsert(random1); arrayRandomInsert(random2); arrayRandomInsert(random3);
        int [] reverse1=new int[1000]; int []reverse2=new int[10000]; int[]reverse3=new int[100000];
        arrayReverseInsert(reverse1); arrayReverseInsert(reverse2); arrayReverseInsert(reverse3);
        int []arr1=new int[1000]; int[]arr2=new int[1000]; int []arr3=new int[10000]; int []arr4=new int[10000];
        int []arr5=new int[100000]; int[]arr6=new int[100000];
        copyOfArray(arr1,random1); copyOfArray(arr2,reverse1); copyOfArray(arr3,random2); copyOfArray(arr4,reverse2);
        copyOfArray(arr5,random3); copyOfArray(arr6,reverse3);
        System.out.printf("%30s%30s%30s%30s%30s%30s\n","Random1000","Reverse1000","Random10000","Reverse10000","Random100000","Reverse100000");
        double num1=bubbleTimeLong(new Sort(arr1)); copyOfArray(arr1,random1);
        double num2=bubbleTimeLong(new Sort(arr2)); copyOfArray(arr2,reverse1);
        double num3=bubbleTimeLong(new Sort(arr3)); copyOfArray(arr3,random2);
        double num4=bubbleTimeLong(new Sort(arr4)); copyOfArray(arr4,reverse2);
        double num5=bubbleTimeLong(new Sort(arr5)); copyOfArray(arr5,random3);
        double num6=bubbleTimeLong(new Sort(arr6)); copyOfArray(arr6,reverse3);
        System.out.print("Bubble sort");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
        num1=selectTimeLong(new Sort(arr1)); copyOfArray(arr1,random1);
        num2=selectTimeLong(new Sort(arr2)); copyOfArray(arr2,reverse1);
        num3=selectTimeLong(new Sort(arr3)); copyOfArray(arr3,random2);
        num4=selectTimeLong(new Sort(arr4)); copyOfArray(arr4,reverse2);
        num5=selectTimeLong(new Sort(arr5)); copyOfArray(arr5,random3);
        num6=selectTimeLong(new Sort(arr6)); copyOfArray(arr6,reverse3);
        System.out.print("Select sort");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
        num1=insertTimeLong(new Sort(arr1)); copyOfArray(arr1,random1);
        num2=insertTimeLong(new Sort(arr2)); copyOfArray(arr2,reverse1);
        num3=insertTimeLong(new Sort(arr3)); copyOfArray(arr3,random2);
        num4=insertTimeLong(new Sort(arr4)); copyOfArray(arr4,reverse2);
        num5=insertTimeLong(new Sort(arr5)); copyOfArray(arr5,random3);
        num6=insertTimeLong(new Sort(arr6)); copyOfArray(arr6,reverse3);
        System.out.print("Insert sort");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
        num1=mergeTimeLong(new Sort(arr1)); copyOfArray(arr1,random1);
        num2=mergeTimeLong(new Sort(arr2)); copyOfArray(arr2,reverse1);
        num3=mergeTimeLong(new Sort(arr3)); copyOfArray(arr3,random2);
        num4=mergeTimeLong(new Sort(arr4)); copyOfArray(arr4,reverse2);
        num5=mergeTimeLong(new Sort(arr5)); copyOfArray(arr5,random3);
        num6=mergeTimeLong(new Sort(arr6)); copyOfArray(arr6,reverse3);
        System.out.print("Merge sort ");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
        num1=quick1TimeLong(new Sort(arr1)); copyOfArray(arr1,random1);
        num2=quick1TimeLong(new Sort(arr2)); copyOfArray(arr2,reverse1);
        num3=quick1TimeLong(new Sort(arr3)); copyOfArray(arr3,random2);
        num4=quick1TimeLong(new Sort(arr4)); copyOfArray(arr4,reverse2);
        //num5=quick1TimeLong(new Sort(arr5)); copyOfArray(arr5,random3);
        //num6=quick1TimeLong(new Sort(arr6)); copyOfArray(arr6,reverse3);
        System.out.print("Quick sort1");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
        num1=quick2TimeLong(new Sort(arr1)); copyOfArray(arr1,random1);
        num2=quick2TimeLong(new Sort(arr2)); copyOfArray(arr2,reverse1);
        num3=quick2TimeLong(new Sort(arr3)); copyOfArray(arr3,random2);
        num4=quick2TimeLong(new Sort(arr4)); copyOfArray(arr4,reverse2);
        //num5=quick2TimeLong(new Sort(arr5)); copyOfArray(arr5,random3);
        //num6=quick2TimeLong(new Sort(arr6)); copyOfArray(arr6,reverse3);
        System.out.print("Quick sort2");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
        num1=quick3TimeLong(new Sort(arr1)); copyOfArray(arr1,random1);
        num2=quick3TimeLong(new Sort(arr2)); copyOfArray(arr2,reverse1);
        num3=quick3TimeLong(new Sort(arr3)); copyOfArray(arr3,random2);
        num4=quick3TimeLong(new Sort(arr4)); copyOfArray(arr4,reverse2);
        //num5=quick3TimeLong(new Sort(arr5)); copyOfArray(arr5,random3);
        //num6=quick3TimeLong(new Sort(arr6)); copyOfArray(arr6,reverse3);
        System.out.print("Quick sort3");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
        num1=heapTimeLong(new Sort(arr1)); copyOfArray(arr1,random1);
        num2=heapTimeLong(new Sort(arr2)); copyOfArray(arr2,reverse1);
        num3=heapTimeLong(new Sort(arr3)); copyOfArray(arr3,random2);
        num4=heapTimeLong(new Sort(arr4)); copyOfArray(arr4,reverse2);
        num5=heapTimeLong(new Sort(arr5)); copyOfArray(arr5,random3);
        num6=heapTimeLong(new Sort(arr6)); copyOfArray(arr6,reverse3);
        System.out.print("Heap sort  ");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
        num1=libraryTimeLong(arr1); num2=libraryTimeLong(arr2); num3=libraryTimeLong(arr3); num4=libraryTimeLong(arr4);
        num5=libraryTimeLong(arr5); num6=libraryTimeLong(arr6);
        System.out.print("Library sort");
        System.out.printf("%20f%29f%30f%30f%30f%30f\n",num1,num2,num3,num4,num5,num6);
    }
    private static void arrayRandomInsert(int []arr){
        Random random=new Random();
        for(int i=0;i<arr.length;i++) arr[i]=random.nextInt(arr.length)+1;//각 배열의 길이만큼 난수 생성.
    }
    private static void arrayReverseInsert(int[]arr){
        for(int i=0;i<arr.length;i++) arr[i]=arr.length-i;
    }
    private static void copyOfArray(int[]arr1,int[]arr2){
        System.arraycopy(arr2,0,arr1,0,arr2.length);
    }
    private static double bubbleTimeLong(Sort sort){
        Instant start=Instant.now();
        sort.bubbleSort();
        Instant end=Instant.now();
        Duration between=Duration.between(start,end);
        return(double)between.toMillis()/1000.0;
    }
    private static double selectTimeLong(Sort sort){
        Instant start=Instant.now();
        sort.selectionSort();
        Instant end=Instant.now();
        Duration beween=Duration.between(start,end);
        return (double)beween.toMillis()/1000.0;
    }
    private static double insertTimeLong(Sort sort){
        Instant start=Instant.now();
        sort.insertSort();
        Instant end=Instant.now();
        Duration between=Duration.between(start,end);
        return (double)between.toMillis()/1000.0;
    }
    private static double mergeTimeLong(Sort sort){
        int num=sort.getLength();
        Instant start=Instant.now();
        sort.mergeSort(0,num);
        Instant end=Instant.now();
        Duration between=Duration.between(start,end);
        return (double)between.toMillis()/1000.0;
    }
    private static double quick1TimeLong(Sort sort){
        int num=sort.getLength();
        Instant start=Instant.now();
        sort.quickSort1(0,num);
        Instant end=Instant.now();
        Duration between=Duration.between(start,end);
        return (double)between.toMillis()/1000.0;
    }
    private static double quick2TimeLong(Sort sort){
        int num=sort.getLength();
        Instant start=Instant.now();
        sort.quickSort2(0,num);
        Instant end=Instant.now();
        Duration between=Duration.between(start,end);
        return (double)between.toMillis()/1000.0;
    }
    private static double quick3TimeLong(Sort sort){
        int num=sort.getLength();
        Instant start=Instant.now();
        sort.quickSort3(0,num);
        Instant end=Instant.now();
        Duration between=Duration.between(start,end);
        return (double)between.toMillis()/1000.0;
    }
    private static double heapTimeLong(Sort sort){
        Instant start=Instant.now();
        sort.heapSort();
        Instant end=Instant.now();
        Duration between=Duration.between(start,end);
        return (double)between.toMillis()/1000.0;
    }
    private static double libraryTimeLong(int[]arr){
        Instant start=Instant.now();
        Arrays.sort(arr);
        Instant end=Instant.now();
        Duration between=Duration.between(start,end);
        return (double)between.toMillis()/1000.0;
    }
}