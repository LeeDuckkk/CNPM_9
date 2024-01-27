#include<bits/stdc++.h>
using namespace std;
vector<int> binhPhuongSoLon(int n){
	int carry = 0;
	vector<int> binhPhuong(2*n-1);
	for(int i=1;i<=2*n-1;i++){
		if(i<=n){
			int sum = i + carry;
			carry = sum/10;
			binhPhuong[i] = sum%10;
		}
		else{
			int sum = 2*n-i+carry;
			carry = sum/10;
			binhPhuong[i] = sum%10;
		}
	}
	return binhPhuong;
}
int main(){
	int t;
	cin>>t;
	while(t--){
		int n;
		cin>>n;
		vector<int> binhPhuong = binhPhuongSoLon(n);
		for(int i=binhPhuong.size();i>=1;i--)
		cout<<binhPhuong[i];
		cout<<endl;
	}
}
