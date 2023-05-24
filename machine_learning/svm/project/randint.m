function [rn,rnsort] = randint(n1,n2,ntr)
% RANDINT generates ntr random integers between n1 and n2 
% Written by Vojislav Kecman - The University of Auckland, New Zealand
% First version written in Yugoslavia, 1990
%
% Program za generiranje _ntr_ slucajnih integera izmedju 
% broja n1 i n2 tj izmedju _ndata_ brojeva od n1 do n2
% 	Copyright (c) 1990-2000 by Vojislav KECMAN

if n1 == 0;		n1 = 1;		end
if ntr > n2-n1;	ntr=n2-n1+1;	end 
ndata = n2-n1+1;
rn(1,1) = ceil(rand*ndata);

i = 1;
while i <= ntr-1
	i=i+1;
	rn1=ceil(rand*ndata);
	rn2=rn1*ones(size(rn));
	
	dif_r=rn-rn2;
	ind=find(dif_r);
	
	if size(dif_r) == size(ind)
	rn(i,1) = rn1;else;i=i-1;end
end

rn = rn+n1-1;	if rn == 0;	rn = [];	end

rnsort=sort(rn);
