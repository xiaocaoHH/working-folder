function [G] = grbf(Data,center,s)
% GRBF calculates the design matrix G(# of data, # of centers). Auckland 2002.
% GRBF is newest and often QUICKER version than GRBF0. (GRBF0 was developed in DA in 1993-94).
% s is a ROW vector containing STD. DEVS. IN ALL DIMENSIONS., that can be different but
% ALL THE GAUSSIANs HAVE EQUAL s
% Written by Vojislav Kecman - The University of Auckland, New Zealand
% First versions written in Yugoslavia and Germany
%	function [G] = grbf(Data,center,stddev);
% 		Program description:
% 		Inputs:
% 		Data		Input pattern for training
% 		center		Matrix of the Gaussian centers
%   		s		Row of standard deviations
%	Output:  
%		matrix G
% 				Copyright (c) 1991-2002 by Vojislav KECMAN

[lnsd,colsd] = size(Data);	
[lnsc,colsc] = size(center);	if colsc ~= colsd;	disp('Data end centers must have same dimension');	return;	end;
[lnss,colss] = size(s);	if colss ~= colsd;	disp('Data, centers and width parameters  must have same dimension');	return;	end;

n = lnsd; dim = colsd;
one = ones(lnsc,dim);
invsigsq = diag(1./(s.^2));

for i = 1:n
	disti =one *diag(Data(i,:)) - center;
%	ind = find(disti==0); % we use this ind in the case that some sigmas are equal 0 
	exp1 = (disti.^2)*invsigsq;
%	exp1(ind)=0;
	if colsd == 1
		G(i,:) = exp(-0.5*exp1');
	else
		G(i,:) = exp(-0.5*sum(exp1'));
	end
end

