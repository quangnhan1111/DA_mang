#include <winsock2.h>
#pragma comment(lib, "IPHLPAPI.lib")
#include <conio.h>
#define MALLOC(x) HeapAlloc(GetProcessHeap(), 0, (x))
#define FREE(x) HeapFree(GetProcessHeap(), 0, (x))
#include <iostream>
#include <string>
#include <stdio.h>
#include <tchar.h> 
#ifndef _WIN32_WINNT 
#define _WIN32_WINNT 0x0600 
#endif
#include <iphlpapi.h> 
#include <ws2tcpip.h> 
using namespace std;
#pragma comment(lib, "ws2_32.lib") 
#pragma comment(lib, "iphlpapi.lib") 

using namespace std;

/// <summary>
/// Sử dụng tham số pAdapterInfo để chuyển một con trỏ đến 
/// bộ đệm do ứng dụng cung cấp để nhận cấu trúc dữ liệu ADAPTER_INFO với thông tin cấu hình Adapter
/// </summary>
IP_ADAPTER_INFO* pAdapterInfo;

//Interface info 
PIP_INTERFACE_INFO interfaceInfo;
/* variables used for GetNetworkParams */

//Khai báo một con trỏ đến một FIXED_INFO đối tượng gọi pFixedInfo, và một ULONG đối tượng gọi ulOutBufLen chứa thông tin. 
//Các biến này được chuyển dưới dạng tham số cho hàm GetNetworkParams.
//Đồng thời tạo một biến DWORD dwRetVal(được sử dụng để kiểm tra lỗi).
FIXED_INFO* pFixedInfo;

ULONG            ulOutBufLen;
DWORD            dwRetVal;
// unsign int


/* variables used to print DHCP time info */
struct tm newtime;
char buffer[32];
errno_t error;


//Return value
unsigned long retVal = 0;

//Loop counter
unsigned int i = 0;

//Set the flags to pass to GetAdaptersAddresses
unsigned long adaptFlags = GAA_FLAG_INCLUDE_PREFIX;


/*Tham số Family cho biết họ địa chỉ nào nên được liệt kê.Các giá trị hợp lệ là :
AF_INET, AF_INET6 hoặc AF_UNSPEC, tùy thuộc vào việc bạn muốn IPv4, IPv6 hay tất cả thông tin IP.*/

//Default to unspecified address family (both)
unsigned long adaptFamily = AF_UNSPEC;


//Hai tham số cuối cùng là bộ đệm mà thông tin IP được trả về và độ dài của bộ đệm.
//Pointer to structure of adapter addresses
PIP_ADAPTER_ADDRESSES adaptAddresses = NULL;

//Length of that buffer
unsigned long outBufLen = 15000;

//Wide character string and size
wchar_t buff[100];
unsigned long bufflen = 100;

void enter_to_continue() {
	cout << "Press Enter to Continue";
	cin.ignore();
}



void call_ip_config() {
	/// <summary>
	/// Nếu bộ đệm của bạn không đủ lớn, GetAdaptersInfo () trả về ERROR_BUFFER_ OVERFLOW và đặt tham số pOutBufLen thành kích thước bộ đệm cần thiết.
	/// </summary>
	if ((dwRetVal = GetAdaptersInfo(pAdapterInfo, &ulOutBufLen)) != ERROR_SUCCESS) {
		printf("GetAdaptersInfo call failed with %d\n", dwRetVal);
	}
	else {
		/// <summary>
		/// Cấu trúc IP_ADAPTER_INFO thực sự là một danh sách các cấu trúc chứa thông tin cấu hình IPv4 cụ thể cho mọi bộ điều hợp mạng
		///  có sẵn trên máy của bạn
		/// </summary>
		PIP_ADAPTER_INFO pAdapter = pAdapterInfo;
		//GetAdaptersInfo() trả về rất nhiều thông tin về bộ điều hợp vật lý và các địa chỉ IPv4 được gán cho nó,
		//nhưng nó không trả về bất kỳ thông tin IPv6 nào.Thay vào đó, một API GetAdaptersAddresses() mới đã được giới thiệu để lấp đầy 
		//khoảng trống này vì nó trả về thông tin địa chỉ cho cả IPv4 và IPv6.

		//Pointer to structure of adapter addresses
		PIP_ADAPTER_ADDRESSES currAddress = NULL;

		//Pointer to unicast address
		PIP_ADAPTER_UNICAST_ADDRESS unicastAddress = NULL;

		//Allocate some memory
		adaptAddresses = (IP_ADAPTER_ADDRESSES*)HeapAlloc(GetProcessHeap(), 0, (outBufLen));

		//Try to get the adapter information
		retVal = GetAdaptersAddresses(adaptFamily, adaptFlags, NULL, adaptAddresses, &outBufLen);

		//If not enough memory
		if (retVal == ERROR_BUFFER_OVERFLOW)
		{
			//Free the memory
			HeapFree(GetProcessHeap(), 0, (adaptAddresses));
			adaptAddresses = NULL;
			return;
		}

		//If successful
		if (retVal == NO_ERROR)
		{
			currAddress = adaptAddresses;
			PIP_ADAPTER_INFO pAdapter_du_phong = pAdapter;
			PIP_ADAPTER_INFO pAdapter_du_phong1 = pAdapter;
			printf("Windows IP Configuration\n\n\n");
			while (currAddress && pAdapter)
			{
				/// <summary>
				/// Chứa mô tả chuỗi Unicode của bộ điều hợp thường dễ đọc hơn so với trường Mô tả
				/// </summary>
				printf("%ws\n", currAddress->FriendlyName);
				/// <summary>
				/// 10.	Mô tả Chứa mô tả chuỗi Unicode của bộ điều hợp
				/// 9.	DnsSuffix: Chỉ định chuỗi hậu tố DNS Unicode được liên kết với bộ điều hợp này.
				/// </summary>
				printf("\tConnection-specific DNS Suffix  .: %ws\n", currAddress->DnsSuffix);




				//Get the first unicast address
				unicastAddress = currAddress->FirstUnicastAddress;
				int dem = 0;
				//If it's not null
				if (unicastAddress != NULL)
				{
					//Loop through the addresses
					for (i = 0; unicastAddress != NULL; i++)
					{
						//If v4 address
						if (unicastAddress->Address.lpSockaddr->sa_family == AF_INET)
						{
							//Get the socket address
							sockaddr_in* sa_in = (sockaddr_in*)unicastAddress->Address.lpSockaddr;

							//Get the v4 address
							InetNtopW(AF_INET, &(sa_in->sin_addr), buff, bufflen);


							printf("\tIPv4 Address  . . . . . . . . . . . .: %ws\n", buff);
						} //Ends the if
						//If v6 address
						else if (unicastAddress->Address.lpSockaddr->sa_family == AF_INET6)
						{
							//Get the socket address
							sockaddr_in6* sa_in6 = (sockaddr_in6*)unicastAddress->Address.lpSockaddr;

							//Get the v6 address
							InetNtopW(AF_INET6, &(sa_in6->sin6_addr), buff, bufflen);
							dem++;
							if (dem == 1)
							{
								printf("\tIPv6 Address  . . . . . . . . . . . . . : %ws\n", buff);
							}
							else if (dem == 2)
							{
								printf("\tTemporary IPv6 Address. . . . . . . . .: %ws\n", buff);
							}
							else
							{
								printf("\tLink-local IPv6 Address  . . . . . . . .: %ws\n", buff);
							}

						} //Ends the if
						//Else, do nothing
						else {}
						//Move on to the next address
						unicastAddress = unicastAddress->Next;
					} //Ends the for
				} //Ends the if	


				wstring a = currAddress->Description;
				string  b(a.begin(), a.end());
				char* ch = (char*)b.c_str();
				while (pAdapter_du_phong)
				{
					string b1 = pAdapter_du_phong->Description;
					char* ch1 = (char*)b1.c_str();
					//cout << ch1 << endl << ch << endl;
					if (strcmp(ch, ch1) == 0)
					{
						printf("\tSubnet Mask . . . . . . . . . . . : %s\n", pAdapter_du_phong->IpAddressList.IpMask.String);
						printf("\tDefault Gateway . . . . . . . . . : %s\n", pAdapter_du_phong->GatewayList.IpAddress.String);
					}
					pAdapter_du_phong = pAdapter_du_phong->Next;
				}
				pAdapter_du_phong = pAdapter_du_phong1;


				printf("\n\n\n");
				//Next address
				currAddress = currAddress->Next;
				pAdapter = pAdapter->Next;
			} //Ends the while
		} //Ends the if
	}
}


void call_ip_config_all() {



	PIP_ADAPTER_INFO pAdapter = pAdapterInfo;

	//Pointer to structure of adapter addresses
	PIP_ADAPTER_ADDRESSES currAddress = NULL;

	//Pointer to unicast address
	PIP_ADAPTER_UNICAST_ADDRESS unicastAddress = NULL;

	//Allocate some memory
	adaptAddresses = (IP_ADAPTER_ADDRESSES*)HeapAlloc(GetProcessHeap(), 0, (outBufLen));

	//Try to get the adapter information+
	retVal = GetAdaptersAddresses(adaptFamily, adaptFlags, NULL, adaptAddresses, &outBufLen);

	if ((dwRetVal = GetAdaptersInfo(pAdapterInfo, &ulOutBufLen)) != ERROR_SUCCESS) {
		printf("GetAdaptersInfo call failed with %d\n", dwRetVal);
	}



	else {

		printf("Windows IP Configuration\n\n\n");

		/// <summary>
		/// Cấp phát bộ nhớ cho các cấu trúc
		/// </summary>
		pFixedInfo = (FIXED_INFO*)MALLOC(sizeof(FIXED_INFO));
		ulOutBufLen = sizeof(FIXED_INFO);
		if (pFixedInfo == NULL) {
			printf("Error allocating memory needed to call GetNetworkParams\n");

		}

		/// <summary>
		/// Thực hiện cuộc gọi ban đầu tới GetNetworkParams để nhận kích thước cần thiết cho biến ulOutBufLen
		/// Hàm chức năng này sẽ không thành công và được sử dụng để 
		/// đảm bảo rằng biến ulOutBufLen chỉ định kích thước đủ để chứa tất cả dữ liệu trả về pFixedInfo 
		/// </summary>
		if (GetNetworkParams(pFixedInfo, &ulOutBufLen) == ERROR_BUFFER_OVERFLOW) {
			FREE(pFixedInfo);
			pFixedInfo = (FIXED_INFO*)MALLOC(ulOutBufLen);
			if (pFixedInfo == NULL) {
				printf("Error allocating memory needed to call GetNetworkParams\n");

			}
		}
		/// <summary>
		/// Thực hiện cuộc gọi thứ hai tới GetNetworkParams bằng cách sử dụng kiểm tra lỗi chung và trả lại giá trị của nó cho biến DWORD dwRetVal ; 
		/// được sử dụng để kiểm tra lỗi nâng cao hơn.
		/// </summary>
		if (dwRetVal = GetNetworkParams(pFixedInfo, &ulOutBufLen) != NO_ERROR) {
			printf("GetNetworkParams failed with error %d\n", dwRetVal);
			if (pFixedInfo)
				FREE(pFixedInfo);

		}

		/// <summary>
		/// Nếu cuộc gọi thành công, hãy truy cập dữ liệu từ cấu trúc dữ liệu pFixedInfo .
		/// </summary>
		else {
			printf("\tHost Name . . . . . . . . . . . . :%s\n", pFixedInfo->HostName);
			printf("\tDomain Name . . . . . . . . . . . . : %s\n", pFixedInfo->DomainName);
			printf("\tDNS Servers . . . . . . . . . . . . :");
			printf("%s\n", pFixedInfo->DnsServerList.IpAddress.String);
			IP_ADDR_STRING* pIPAddr;
			pIPAddr = pFixedInfo->DnsServerList.Next;
			while (pIPAddr) {
				printf("\t\t%s\n", pIPAddr->IpAddress.String);
				pIPAddr = pIPAddr->Next;
			}
			printf("\tNode Type: ");
			switch (pFixedInfo->NodeType) {
			case 1:
				printf("%s\n", "Broadcast");
				break;
			case 2:
				printf("%s\n", "Peer to peer");
				break;
			case 4:
				printf("%s\n", "Mixed");
				break;
			case 8:
				printf("%s\n", "Hybrid");
				break;
			default:
				printf("\n");
			}
			printf("\tIP Routing Enabled. . . . . . . . :%s\n", pFixedInfo->EnableRouting ? "Yes" : "No");
			printf("\tWINS Proxy Enabled. . . . . . . . :%s\n", pFixedInfo->EnableProxy ? "Yes" : "No");
		}



		//Current adapter information
		currAddress = adaptAddresses;
		printf("Windows IP Configuration\n\n\n");
		//Loop through all of the adapters
		PIP_ADAPTER_INFO pAdapter_du_phong = pAdapter;
		PIP_ADAPTER_INFO pAdapter_du_phong1 = pAdapter;
		while (currAddress && pAdapter)
		{
			printf("%ws\n", currAddress->FriendlyName);
			printf("\tConnection-specific DNS Suffix  .: %ws\n", currAddress->DnsSuffix);
			printf("\tDescription. . . .  . . . . . .  .: %ws\n", currAddress->Description);
			//			IN ra dia chi MAC
			printf("\tPhysical Address. . . . . . . . . : ");


			if (currAddress->PhysicalAddressLength != 0)
			{
				//Loop through the bytes of the address
				for (i = 0; i < (int)currAddress->PhysicalAddressLength; i++)
				{
					//If it's the end of the address
					if (i == (currAddress->PhysicalAddressLength - 1))
					{
						printf("%.2X\n", (int)currAddress->PhysicalAddress[i]);
					}
					else
					{
						printf("%.2X-", (int)currAddress->PhysicalAddress[i]);
					}
				}

			}
			printf("\tDHCP Enabled. . . . . . . . . . . : %s\n", pAdapter->DhcpEnabled ? "YES" : "NO");

			//lay ipv4 va ipv6
			//Get the first unicast address
			unicastAddress = currAddress->FirstUnicastAddress;
			int dem = 0;
			//If it's not null
			if (unicastAddress != NULL)
			{
				//Loop through the addresses
				for (i = 0; unicastAddress != NULL; i++)
				{
					//If v4 address
					if (unicastAddress->Address.lpSockaddr->sa_family == AF_INET)
					{
						//Get the socket address
						sockaddr_in* sa_in = (sockaddr_in*)unicastAddress->Address.lpSockaddr;

						//Get the v4 address
						InetNtopW(AF_INET, &(sa_in->sin_addr), buff, bufflen);


						printf("\tIPv4 Address  . . . . . . . . . . . .: %ws\n", buff);
					} //Ends the if
					//If v6 address
					else if (unicastAddress->Address.lpSockaddr->sa_family == AF_INET6)
					{
						//Get the socket address
						sockaddr_in6* sa_in6 = (sockaddr_in6*)unicastAddress->Address.lpSockaddr;

						//Get the v6 address
						InetNtopW(AF_INET6, &(sa_in6->sin6_addr), buff, bufflen);
						dem++;
						if (dem == 1)
						{
							printf("\tIPv6 Address  . . . . . . . . . . . . . : %ws\n", buff);
						}
						else if (dem == 2)
						{
							printf("\tTemporary IPv6 Address. . . . . . . . .: %ws\n", buff);
						}
						else
						{
							printf("\tLink-local IPv6 Address  . . . . . . . .: %ws\n", buff);
						}

					} //Ends the if
					//Else, do nothing
					else {}
					//Move on to the next address
					unicastAddress = unicastAddress->Next;
				} //Ends the for
			} //Ends the if	


			wstring a = currAddress->Description;
			string  b(a.begin(), a.end());
			char* ch = (char*)b.c_str();
			while (pAdapter_du_phong)
			{
				string b1 = pAdapter_du_phong->Description;
				char* ch1 = (char*)b1.c_str();
				//cout << ch1 << endl << ch << endl;
				if (strcmp(ch, ch1) == 0)
				{
					printf("\tSubnet Mask . . . . . . . . . . . : %s\n", pAdapter_du_phong->IpAddressList.IpMask.String);
					printf("\tDefault Gateway . . . . . . . . . : %s\n", pAdapter_du_phong->GatewayList.IpAddress.String);
					printf("\tDHCP Server . . . . . . . . . . . : %s\n", pAdapter_du_phong->DhcpServer.IpAddress.String);
				}
				pAdapter_du_phong = pAdapter_du_phong->Next;
			}
			pAdapter_du_phong = pAdapter_du_phong1;
			printf("\n");

			if (pAdapter->DhcpEnabled) {
				printf("\tLease Obtained: ");
				/* Display local time */
				error = _localtime32_s(&newtime, (__time32_t*)&pAdapter->LeaseObtained);
				if (error)
					printf("Invalid Argument to _localtime32_s\n");
				else {
					// Convert to an ASCII representation 
					error = asctime_s(buffer, 32, &newtime);
					if (error)
						printf("Invalid Argument to asctime_s\n");
					else
						/* asctime_s returns the string terminated by \n\0 */
						printf("%s", buffer);
				}

				printf("\tLease Expires:  ");
				error = _localtime32_s(&newtime, (__time32_t*)&pAdapter->LeaseExpires);
				if (error)
					printf("Invalid Argument to _localtime32_s\n");
				else {
					// Convert to an ASCII representation 
					error = asctime_s(buffer, 32, &newtime);
					if (error)
						printf("Invalid Argument to asctime_s\n");
					else
						/* asctime_s returns the string terminated by \n\0 */
						printf("%s", buffer);
				}
			}

			printf("\tDNS Servers . . . . . . . . . . . . :");
			printf("%s\n", pFixedInfo->DnsServerList.IpAddress.String);
			IP_ADDR_STRING* pIPAddr;
			pIPAddr = pFixedInfo->DnsServerList.Next;
			while (pIPAddr) {
				printf("\t%s\n", pIPAddr->IpAddress.String);
				pIPAddr = pIPAddr->Next;
			}

			printf("\tNetBIOS over Tcpip. . . . . . . . : %s\n\n", currAddress->NetbiosOverTcpipEnabled == 1 ? "Enable" : "Disable");
			printf("\n\n\n");
			//Next address
			currAddress = currAddress->Next;

			pAdapter = pAdapter->Next;
		} //Ends the while
	} //Ends the if
}








void call_renew() {
	ULONG ulOutBufLen = 0;
	DWORD dwRetVal = 0;
	PIP_INTERFACE_INFO pInfo;
	pInfo = (IP_INTERFACE_INFO*)MALLOC(sizeof(IP_INTERFACE_INFO));

	if (GetInterfaceInfo(pInfo, &ulOutBufLen) == ERROR_INSUFFICIENT_BUFFER) {
		FREE(pInfo);
		pInfo = (IP_INTERFACE_INFO*)MALLOC(ulOutBufLen);
	}


	if ((dwRetVal = GetInterfaceInfo(pInfo, &ulOutBufLen)) == NO_ERROR) {
		printf("\nCall ipconfig /renew...\n");
	}
	else if (dwRetVal == ERROR_NO_DATA) {
		printf("There are no network adapters with IPv4 enabled on the local system\n");
		return;
	}

	else {
		LPVOID lpMsgBuf;
		printf("GetInterfaceInfo failed.\n");

		if (FormatMessage(
			FORMAT_MESSAGE_ALLOCATE_BUFFER |
			FORMAT_MESSAGE_FROM_SYSTEM |
			FORMAT_MESSAGE_IGNORE_INSERTS,
			NULL,
			dwRetVal,
			MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), // Default language
			(LPTSTR)&lpMsgBuf,
			0,
			NULL)) {

			printf("\tError: %s", lpMsgBuf);
		}
		LocalFree(lpMsgBuf);
		return;
	}

	// Call IpReleaseAddress and IpRenewAddress to release and renew
	// the IP address on the first network adapter returned 
	// by the call to GetInterfaceInfo.
	PIP_INTERFACE_INFO pAdapter = pInfo;
	int isSucces = 0;
	for (int i = 0; i < pAdapter->NumAdapters; i++) {
		if ((dwRetVal = IpRenewAddress(&pInfo->Adapter[i])) == NO_ERROR) {
			isSucces = 1;
		}
	}




	call_ip_config();




	if (isSucces == 1) {
		printf("\n\nipconfig /renew succeeded.\n");
	}
	else {
		printf("\n\nipconfig /renew failed.\n");
	}
	if (pInfo != NULL) {
		FREE(pInfo);
	}
	return;
}
void call_release() {
	ULONG ulOutBufLen = 0;
	DWORD dwRetVal = 0;
	PIP_INTERFACE_INFO pInfo;
	pInfo = (IP_INTERFACE_INFO*)MALLOC(sizeof(IP_INTERFACE_INFO));

	// Make an initial call to GetInterfaceInfo to get
	// the necessary size into the ulOutBufLen variable
	if (GetInterfaceInfo(pInfo, &ulOutBufLen) == ERROR_INSUFFICIENT_BUFFER) {
		FREE(pInfo);
		pInfo = (IP_INTERFACE_INFO*)MALLOC(ulOutBufLen);
	}

	// Make a second call to GetInterfaceInfo to get the
	// actual data we want
	if ((dwRetVal = GetInterfaceInfo(pInfo, &ulOutBufLen)) == NO_ERROR) {
		//printf("\tAdapter Name: %ws\n", pInfo->Adapter[0].Name);
		//printf("\tAdapter Index: %ld\n", pInfo->Adapter[0].Index);
		//printf("\tNum Adapters: %ld\n", pInfo->NumAdapters);
		printf("\nCall ipconfig /release...\n");
	}
	else if (dwRetVal == ERROR_NO_DATA) {
		printf("There are no network adapters with IPv4 enabled on the local system\n");
		return;
	}
	else {
		LPVOID lpMsgBuf;
		printf("GetInterfaceInfo failed.\n");

		if (FormatMessage(
			FORMAT_MESSAGE_ALLOCATE_BUFFER |
			FORMAT_MESSAGE_FROM_SYSTEM |
			FORMAT_MESSAGE_IGNORE_INSERTS,
			NULL,
			dwRetVal,
			MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), // Default language
			(LPTSTR)&lpMsgBuf,
			0,
			NULL)) {
			printf("\tError: %s", lpMsgBuf);
		}
		LocalFree(lpMsgBuf);
		return;
	}

	// Call IpReleaseAddress and IpRenewAddress to release and renew
	// the IP address on the first network adapter returned 
	// by the call to GetInterfaceInfo.
	PIP_INTERFACE_INFO pAdapter = pInfo;
	int isSucces = 0;
	for (int i = 0; i < pAdapter->NumAdapters; i++) {
		if ((dwRetVal = IpReleaseAddress(&pInfo->Adapter[i])) == NO_ERROR) {
			isSucces = 1;
		}
	}
	call_ip_config();
	if (isSucces == 1) {
		printf("\n\nipconfig /release succeeded.\n");
	}
	else {
		printf("\n\nipconfig /release failed.\n");
	}
	if (pInfo != NULL) {
		FREE(pInfo);
	}
	return;
}
int main()
{
	string menu = "";
	int chon_menu = 0;
	pAdapterInfo = (IP_ADAPTER_INFO*)malloc(sizeof(IP_ADAPTER_INFO));
	ulOutBufLen = sizeof(IP_ADAPTER_INFO);

	if (GetAdaptersInfo(pAdapterInfo, &ulOutBufLen) != ERROR_SUCCESS) {
		free(pAdapterInfo);
		pAdapterInfo = (IP_ADAPTER_INFO*)malloc(ulOutBufLen);
	}
	do {
		cout << "Menu: " << endl;
		cout << "\tOption 1: ipconfig" << endl;
		cout << "\tOption 2: ipconfig /all" << endl;
		cout << "\tOption 3: ipconfig /renew" << endl;
		cout << "\tOption 4: ipconfig /release" << endl;
		cout << "\press 0 to exit" << endl;
		cout << "Option: ";
		cin >> chon_menu;
		switch (chon_menu)
		{
		case 1:
			call_ip_config();
			break;
		case 2:
			call_ip_config_all();
			break;
		case 3:
			call_renew();
			break;
		case 4:
			call_release();
			break;
		default:
			break;
		}
	} while (chon_menu != 0);
	getchar();
	return 1;
}





















